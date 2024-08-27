/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { memo, useContext, useMemo } from 'react';
import { EdgeProps, Node, Position, getSmoothStepPath, useReactFlow, useStoreApi } from 'reactflow';
import { NodeData, EdgeData } from '../DiagramRenderer.types';
import { getHandleCoordinatesByPosition } from './EdgeLayout';
import { MultiLabelEdge } from './MultiLabelEdge';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { NodeTypeContext } from '../../contexts/NodeContext';
import { NodeTypeContextValue } from '../../contexts/NodeContext.types';
import { MultiLabelEdgeData } from './MultiLabelEdge.types';
import { getSmartEdge } from './smart-edge/getSmartEdge';

const roundToNearestTen = (num: number): number => Math.round(num / 10) * 10;

const isAncestorOf = (child: Node, candidate: Node, nodeById: (arg0: string) => Node | undefined): boolean => {
  if (child.parentNode === candidate.id) {
    return true;
  } else {
    const childParent: Node | undefined = child.parentNode ? nodeById(child.parentNode) : undefined;
    return childParent !== undefined && isAncestorOf(childParent, candidate, nodeById);
  }
};

const getAncestors = (
  node: Node | undefined,
  nodes: Node[],
  maxAncestorToSearch: Node | undefined = undefined,
  ancestors: Node[] = []
): Node[] => {
  if (!node) {
    return ancestors;
  }
  ancestors.push(node);
  if (node.parentNode || node.id === maxAncestorToSearch?.id) {
    const parentNode = nodes.find((n) => n.id === node.parentNode);
    return getAncestors(parentNode, nodes, maxAncestorToSearch, ancestors);
  } else {
    return ancestors;
  }
};

const getLowestCommunAncestor = (node: Node, nodes: Node[], ancestorIds: string[]): Node | undefined => {
  if (ancestorIds.includes(node.id)) {
    return node;
  }
  if (node.parentNode) {
    const parentNode = nodes.find((n) => n.id === node.parentNode);
    if (parentNode) {
      return getLowestCommunAncestor(parentNode, nodes, ancestorIds);
    } else {
      return undefined;
    }
  } else {
    return undefined;
  }
};

function findLowestCommonAncestor(nodes, sourceNode, targetNode) {
  const sourceAncestorIds = getAncestors(sourceNode, nodes).map((ancestor) => ancestor.id);
  return getLowestCommunAncestor(targetNode, nodes, sourceAncestorIds);
}

export const SmartStepEdgeWrapper = memo((props: EdgeProps<MultiLabelEdgeData>) => {
  const { source, target, markerEnd, markerStart, sourcePosition, targetPosition, sourceHandleId, targetHandleId } =
    props;
  const { nodeLayoutHandlers } = useContext<NodeTypeContextValue>(NodeTypeContext);
  const { getNodes } = useReactFlow<NodeData, EdgeData>();
  const nodes = getNodes();
  const { nodeInternals } = useStoreApi().getState();

  const sourceNode = nodeInternals.get(source);
  const targetNode = nodeInternals.get(target);

  if (!sourceNode || !targetNode) {
    return null;
  }

  const sourceLayoutHandler = nodeLayoutHandlers.find((nodeLayoutHandler) =>
    nodeLayoutHandler.canHandle(sourceNode as Node<NodeData, DiagramNodeType>)
  );
  const targetLayoutHandler = nodeLayoutHandlers.find((nodeLayoutHandler) =>
    nodeLayoutHandler.canHandle(targetNode as Node<NodeData, DiagramNodeType>)
  );

  let { x: sourceX, y: sourceY } = getHandleCoordinatesByPosition(
    sourceNode,
    sourcePosition,
    sourceHandleId ?? '',
    sourceLayoutHandler?.calculateCustomNodeEdgeHandlePosition
  );
  let { x: targetX, y: targetY } = getHandleCoordinatesByPosition(
    targetNode,
    targetPosition,
    targetHandleId ?? '',
    targetLayoutHandler?.calculateCustomNodeEdgeHandlePosition
  );

  // trick to have the source of the edge positioned at the very border of a node
  // if the edge has a marker, then only the marker need to touch the node
  const handleSourceRadius = markerStart == undefined || markerStart.includes('None') ? 2 : 3;
  switch (sourcePosition) {
    case Position.Right:
      sourceX = sourceX + handleSourceRadius;
      sourceY = roundToNearestTen(sourceY);
      break;
    case Position.Left:
      sourceX = sourceX - handleSourceRadius;
      sourceY = roundToNearestTen(sourceY);
      break;
    case Position.Top:
      sourceY = sourceY - handleSourceRadius;
      sourceX = roundToNearestTen(sourceX);
      break;
    case Position.Bottom:
      sourceY = sourceY + handleSourceRadius;
      sourceX = roundToNearestTen(sourceX);
      break;
  }
  // trick to have the target of the edge positioned at the very border of a node
  // if the edge has a marker, then only the marker need to touch the node
  const handleTargetRadius = markerEnd == undefined || markerEnd.includes('None') ? 2 : 3;
  switch (targetPosition) {
    case Position.Right:
      targetX = targetX + handleTargetRadius;
      targetY = roundToNearestTen(targetY);
      break;
    case Position.Left:
      targetX = targetX - handleTargetRadius;
      targetY = roundToNearestTen(targetY);
      break;
    case Position.Top:
      targetY = targetY - handleTargetRadius;
      targetX = roundToNearestTen(targetX);
      break;
    case Position.Bottom:
      targetY = targetY + handleTargetRadius;
      targetX = roundToNearestTen(targetX);
      break;
  }

  const nodeHierarchy = nodes.map((node) => node.id + node.parentNode).join();

  const lowestCommonAncestor = useMemo(() => findLowestCommonAncestor(nodes, sourceNode, targetNode), [nodeHierarchy]);

  const sourceAncestorIds: string[] = useMemo(
    () =>
      getAncestors(
        nodes.find((n) => n.id === sourceNode.parentNode),
        nodes,
        lowestCommonAncestor
      ).map((node) => node.id),
    [nodeHierarchy, lowestCommonAncestor, sourceNode]
  );

  const targetAncestorIds: string[] = useMemo(
    () =>
      getAncestors(
        nodes.find((n) => n.id === targetNode.parentNode),
        nodes,
        lowestCommonAncestor
      ).map((node) => node.id),
    [nodeHierarchy, lowestCommonAncestor, targetNode]
  );

  const nodeIdsToConsider: string[] = useMemo(() => {
    return nodes
      .filter((node) => {
        if (node.id === sourceNode.id || node.id === targetNode.id) {
          return true;
        }
        if (sourceNode.data.isBorderNode && node.id === sourceNode.parentNode) {
          return !targetAncestorIds.includes(sourceNode.parentNode);
        }
        if (targetNode.data.isBorderNode && node.id === targetNode.parentNode) {
          return !sourceAncestorIds.includes(targetNode.parentNode);
        }
        const sourceAncestorSiblings = sourceAncestorIds.includes(node.parentNode ?? '');
        const targetAncestorSiblings = targetAncestorIds.includes(node.parentNode ?? '');
        const isDirectAncestor = sourceAncestorIds.includes(node.id) || targetAncestorIds.includes(node.id);
        return (
          (sourceAncestorSiblings || targetAncestorSiblings || node.parentNode === lowestCommonAncestor) &&
          !isDirectAncestor
        );
      })
      .map((node) => node.id);
  }, [nodeHierarchy, sourceAncestorIds.join(), targetAncestorIds.join()]);

  const nodesPositionToConsider: string = nodes
    .filter((node) => nodeIdsToConsider.includes(node.id))
    .map((node) => node.id + node.position.x + node.position.y + node.width + node.height)
    .join();

  const getSmartEdgeResponse = useMemo(() => {
    const nodesToConsider: Node[] = nodes.filter((node) => nodeIdsToConsider.includes(node.id));
    return getSmartEdge({
      sourceX,
      sourceY,
      sourcePosition,
      targetX,
      targetY,
      targetPosition,
      nodes: nodesToConsider,
    });
  }, [
    nodeIdsToConsider.join(),
    nodesPositionToConsider,
    sourceX,
    sourceY,
    sourcePosition,
    targetX,
    targetY,
    targetPosition,
  ]);

  if (getSmartEdgeResponse === null) {
    const [edgePath, labelX, labelY] = getSmoothStepPath({
      sourceX,
      sourceY,
      sourcePosition,
      targetX,
      targetY,
      targetPosition,
    });
    return (
      <MultiLabelEdge
        {...props}
        sourceX={sourceX}
        sourceY={sourceY}
        targetX={targetX}
        targetY={targetY}
        edgeCenterX={labelX}
        edgeCenterY={labelY}
        svgPathString={edgePath}
      />
    );
  }
  const { edgeCenterX, edgeCenterY, svgPathString } = getSmartEdgeResponse;
  return (
    <MultiLabelEdge
      {...props}
      sourceX={sourceX}
      sourceY={sourceY}
      targetX={targetX}
      targetY={targetY}
      edgeCenterX={edgeCenterX}
      edgeCenterY={edgeCenterY}
      svgPathString={svgPathString}
    />
  );
});