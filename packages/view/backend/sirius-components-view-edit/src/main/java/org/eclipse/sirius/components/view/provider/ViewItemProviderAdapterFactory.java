/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.view.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ChildCreationExtenderManager;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.util.ViewAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers. The adapters generated by this
 * factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}. The adapters
 * also support Eclipse property sheets. Note that most of the adapters are shared among multiple instances. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class ViewItemProviderAdapterFactory extends ViewAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable, IChildCreationExtender {
    /**
     * This keeps track of the root adapter factory that delegates to this adapter factory. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected ComposedAdapterFactory parentAdapterFactory;

    /**
     * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected IChangeNotifier changeNotifier = new ChangeNotifier();

    /**
     * This helps manage the child creation extenders. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ChildCreationExtenderManager childCreationExtenderManager = new ChildCreationExtenderManager(ViewEditPlugin.INSTANCE, ViewPackage.eNS_URI);

    /**
     * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected Collection<Object> supportedTypes = new ArrayList<>();

    /**
     * This constructs an instance. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ViewItemProviderAdapterFactory() {
        this.supportedTypes.add(IEditingDomainItemProvider.class);
        this.supportedTypes.add(IStructuredItemContentProvider.class);
        this.supportedTypes.add(ITreeItemContentProvider.class);
        this.supportedTypes.add(IItemLabelProvider.class);
        this.supportedTypes.add(IItemPropertySource.class);
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.View} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ViewItemProvider viewItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.View}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createViewAdapter() {
        if (this.viewItemProvider == null) {
            this.viewItemProvider = new ViewItemProvider(this);
        }

        return this.viewItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.ColorPalette}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ColorPaletteItemProvider colorPaletteItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.ColorPalette}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createColorPaletteAdapter() {
        if (this.colorPaletteItemProvider == null) {
            this.colorPaletteItemProvider = new ColorPaletteItemProvider(this);
        }

        return this.colorPaletteItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.FixedColor} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected FixedColorItemProvider fixedColorItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.FixedColor}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createFixedColorAdapter() {
        if (this.fixedColorItemProvider == null) {
            this.fixedColorItemProvider = new FixedColorItemProvider(this);
        }

        return this.fixedColorItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.ChangeContext}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ChangeContextItemProvider changeContextItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.ChangeContext}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createChangeContextAdapter() {
        if (this.changeContextItemProvider == null) {
            this.changeContextItemProvider = new ChangeContextItemProvider(this);
        }

        return this.changeContextItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.CreateInstance}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected CreateInstanceItemProvider createInstanceItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.CreateInstance}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createCreateInstanceAdapter() {
        if (this.createInstanceItemProvider == null) {
            this.createInstanceItemProvider = new CreateInstanceItemProvider(this);
        }

        return this.createInstanceItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.SetValue} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected SetValueItemProvider setValueItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.SetValue}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createSetValueAdapter() {
        if (this.setValueItemProvider == null) {
            this.setValueItemProvider = new SetValueItemProvider(this);
        }

        return this.setValueItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.UnsetValue} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected UnsetValueItemProvider unsetValueItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.UnsetValue}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createUnsetValueAdapter() {
        if (this.unsetValueItemProvider == null) {
            this.unsetValueItemProvider = new UnsetValueItemProvider(this);
        }

        return this.unsetValueItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.DeleteElement}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DeleteElementItemProvider deleteElementItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.DeleteElement}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createDeleteElementAdapter() {
        if (this.deleteElementItemProvider == null) {
            this.deleteElementItemProvider = new DeleteElementItemProvider(this);
        }

        return this.deleteElementItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.Let} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected LetItemProvider letItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.Let}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createLetAdapter() {
        if (this.letItemProvider == null) {
            this.letItemProvider = new LetItemProvider(this);
        }

        return this.letItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.If} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected IfItemProvider ifItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.If}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createIfAdapter() {
        if (this.ifItemProvider == null) {
            this.ifItemProvider = new IfItemProvider(this);
        }

        return this.ifItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.For} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ForItemProvider forItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.For}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createForAdapter() {
        if (this.forItemProvider == null) {
            this.forItemProvider = new ForItemProvider(this);
        }

        return this.forItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.TextStylePalette}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TextStylePaletteItemProvider textStylePaletteItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.TextStylePalette}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createTextStylePaletteAdapter() {
        if (this.textStylePaletteItemProvider == null) {
            this.textStylePaletteItemProvider = new TextStylePaletteItemProvider(this);
        }

        return this.textStylePaletteItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.TextStyleDescription}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TextStyleDescriptionItemProvider textStyleDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.TextStyleDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createTextStyleDescriptionAdapter() {
        if (this.textStyleDescriptionItemProvider == null) {
            this.textStyleDescriptionItemProvider = new TextStyleDescriptionItemProvider(this);
        }

        return this.textStyleDescriptionItemProvider;
    }

    /**
     * This returns the root adapter factory that contains this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ComposeableAdapterFactory getRootAdapterFactory() {
        return this.parentAdapterFactory == null ? this : this.parentAdapterFactory.getRootAdapterFactory();
    }

    /**
     * This sets the composed adapter factory that contains this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
        this.parentAdapterFactory = parentAdapterFactory;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object type) {
        return this.supportedTypes.contains(type) || super.isFactoryForType(type);
    }

    /**
     * This implementation substitutes the factory itself as the key for the adapter. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter adapt(Notifier notifier, Object type) {
        return super.adapt(notifier, this);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object adapt(Object object, Object type) {
        if (this.isFactoryForType(type)) {
            Object adapter = super.adapt(object, type);
            if (!(type instanceof Class<?>) || (((Class<?>) type).isInstance(adapter))) {
                return adapter;
            }
        }

        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public List<IChildCreationExtender> getChildCreationExtenders() {
        return this.childCreationExtenderManager.getChildCreationExtenders();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Collection<?> getNewChildDescriptors(Object object, EditingDomain editingDomain) {
        return this.childCreationExtenderManager.getNewChildDescriptors(object, editingDomain);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return this.childCreationExtenderManager;
    }

    /**
     * This adds a listener. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void addListener(INotifyChangedListener notifyChangedListener) {
        this.changeNotifier.addListener(notifyChangedListener);
    }

    /**
     * This removes a listener. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void removeListener(INotifyChangedListener notifyChangedListener) {
        this.changeNotifier.removeListener(notifyChangedListener);
    }

    /**
     * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public void fireNotifyChanged(Notification notification) {
        this.changeNotifier.fireNotifyChanged(notification);

        if (this.parentAdapterFactory != null) {
            this.parentAdapterFactory.fireNotifyChanged(notification);
        }
    }

    /**
     * This disposes all of the item providers created by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void dispose() {
        if (this.viewItemProvider != null)
            this.viewItemProvider.dispose();
        if (this.colorPaletteItemProvider != null)
            this.colorPaletteItemProvider.dispose();
        if (this.fixedColorItemProvider != null)
            this.fixedColorItemProvider.dispose();
        if (this.changeContextItemProvider != null)
            this.changeContextItemProvider.dispose();
        if (this.createInstanceItemProvider != null)
            this.createInstanceItemProvider.dispose();
        if (this.setValueItemProvider != null)
            this.setValueItemProvider.dispose();
        if (this.unsetValueItemProvider != null)
            this.unsetValueItemProvider.dispose();
        if (this.deleteElementItemProvider != null)
            this.deleteElementItemProvider.dispose();
        if (this.letItemProvider != null)
            this.letItemProvider.dispose();
        if (this.ifItemProvider != null)
            this.ifItemProvider.dispose();
        if (this.forItemProvider != null)
            this.forItemProvider.dispose();
        if (this.textStylePaletteItemProvider != null)
            this.textStylePaletteItemProvider.dispose();
        if (this.textStyleDescriptionItemProvider != null)
            this.textStyleDescriptionItemProvider.dispose();
    }

}
