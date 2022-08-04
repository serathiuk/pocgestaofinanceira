<template>
	<div :class="containerClass" @click="onWrapperClick">
        <AppTopBar @menu-toggle="onMenuToggle" />
        <div class="layout-sidebar" @click="onSidebarClick">
            <AppMenu :model="menu" @menuitem-click="onMenuItemClick" />
        </div>

        <div class="layout-main-container">
            <div class="layout-main">
                <router-view />
            </div>
        </div>

        <transition name="layout-mask">
            <div class="layout-mask p-component-overlay" v-if="mobileMenuActive"></div>
        </transition>
	</div>
</template>

<script>
import 'primevue/resources/themes/lara-light-purple/theme.css';

import AppTopBar from './AppTopbar.vue';
import AppMenu from './AppMenu.vue';

export default {
    emits: ['change-theme'],
    data() {
        return {
            layoutMode: 'static',
            staticMenuInactive: false,
            overlayMenuActive: false,
            mobileMenuActive: false,
            menu : [
                {
                    label: 'Geral',
                    items: [{
                        label: 'Início', icon: 'pi pi-fw pi-home', to: '/'
                    }]
                },
				{
					label: 'Financeiro - Movimentos', icon: 'pi pi-fw pi-clone',
					items: [
						{label: 'Lançamento de Fluxo de Caixa', icon: 'pi pi-fw pi-desktop', to: '/lancamentos_fluxo_caixa'},
                        {label: 'Pagar/Receber', icon: 'pi pi-fw pi-desktop', to: '/pagar_receber'},
                        {label: 'Baixas de Pagar e Receber', icon: 'pi pi-fw pi-desktop', to: '/baixas_pagar_receber'},
                        {label: 'Ordem de Pagamento', icon: 'pi pi-fw pi-desktop', to: '/ordemPagamento'},

					]
				},
				{
					label: 'Financeiro - Cadastros', icon: 'pi pi-fw pi-clone',
					items: [
                        {label: 'Contas Bancárias', icon: 'pi pi-fw pi-desktop', to: '/contas_bancarias'},
                        {label: 'Contas Fluxo de Caixa', icon: 'pi pi-fw pi-desktop', to: '/contas_fluxo_caixa'},

					]
				},
                {
					label: '(Dev) Simulador ERP Legado', icon: 'pi pi-fw pi-clone',
					items: [
                        {label: 'Simulação de Venda', icon: 'pi pi-fw pi-desktop', to: '/simulador_venda'},
						{label: 'Filiais', icon: 'pi pi-fw pi-desktop', to: '/filiais'},
                        {label: 'Pessoas', icon: 'pi pi-fw pi-desktop', to: '/pessoas'},
                        
					]
				},
                {
					label: '(Dev) Simulador Integração Bancária', icon: 'pi pi-fw pi-clone',
					items: [
						{label: 'Configurações', icon: 'pi pi-fw pi-desktop', to: '/ib_configuracoes'},
                        {label: 'Status Pagamento', icon: 'pi pi-fw pi-desktop', to: '/ib_status_pagamento'}
					]
				},
            ]
        }
    },
    methods: {
        onWrapperClick() {
            if (!this.menuClick) {
                this.overlayMenuActive = false;
                this.mobileMenuActive = false;
            }

            this.menuClick = false;
        },
        onMenuToggle() {
            this.menuClick = true;

            if (this.isDesktop()) {
                if (this.layoutMode === 'overlay') {
					if(this.mobileMenuActive === true) {
						this.overlayMenuActive = true;
					}

                    this.overlayMenuActive = !this.overlayMenuActive;
					this.mobileMenuActive = false;
                }
                else if (this.layoutMode === 'static') {
                    this.staticMenuInactive = !this.staticMenuInactive;
                }
            }
            else {
                this.mobileMenuActive = !this.mobileMenuActive;
            }

            event.preventDefault();
        },
        onSidebarClick() {
            this.menuClick = true;
        },
        onMenuItemClick(event) {
            if (event.item && !event.item.items) {
                this.overlayMenuActive = false;
                this.mobileMenuActive = false;
            }
        },
		onLayoutChange(layoutMode) {
			this.layoutMode = layoutMode;
		},
        addClass(element, className) {
            if (element.classList)
                element.classList.add(className);
            else
                element.className += ' ' + className;
        },
        removeClass(element, className) {
            if (element.classList)
                element.classList.remove(className);
            else
                element.className = element.className.replace(new RegExp('(^|\\b)' + className.split(' ').join('|') + '(\\b|$)', 'gi'), ' ');
        },
        isDesktop() {
            return window.innerWidth >= 992;
        },
        isSidebarVisible() {
            if (this.isDesktop()) {
                if (this.layoutMode === 'static')
                    return !this.staticMenuInactive;
                else if (this.layoutMode === 'overlay')
                    return this.overlayMenuActive;
            }

            return true;
        }
    },
    computed: {
        containerClass() {
            return ['layout-wrapper', {
                'layout-overlay': this.layoutMode === 'overlay',
                'layout-static': this.layoutMode === 'static',
                'layout-static-sidebar-inactive': this.staticMenuInactive && this.layoutMode === 'static',
                'layout-overlay-sidebar-active': this.overlayMenuActive && this.layoutMode === 'overlay',
                'layout-mobile-sidebar-active': this.mobileMenuActive,
				'p-input-filled': this.$primevue.config.inputStyle === 'filled',
				'p-ripple-disabled': this.$primevue.config.ripple === false
            }];
        },
        logo() {
            return (this.$appState.darkTheme) ? "images/logo-white.svg" : "images/logo.svg";
        }
    },
    beforeUpdate() {
        if (this.mobileMenuActive)
            this.addClass(document.body, 'body-overflow-hidden');
        else
            this.removeClass(document.body, 'body-overflow-hidden');
    },
    components: {
        'AppTopBar': AppTopBar,
        'AppMenu': AppMenu
    }
}
</script>

<style lang="scss">
@import './App.scss';
</style>
