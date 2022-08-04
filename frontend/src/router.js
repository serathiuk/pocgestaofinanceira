import { createRouter, createWebHashHistory } from 'vue-router';
import { Auth } from 'aws-amplify';
import App from './App.vue';

const routes = [
    {
        path: '/',
        name: 'app',
        component: App,
        children: [
            {
                path: '',
                name: 'dashboard',
                component: () => import('./components/Dashboard.vue')
            },
            {
                path: '/pessoas',
                name: 'pessoas',
                component: () => import('./cadastros/pessoas/Pessoas.vue')
            },
            {
                path: '/filiais',
                name: 'filiais',
                component: () => import('./cadastros/filiais/Filiais.vue')
            },
            {
                path: '/contas_bancarias',
                name: 'contas_bancarias',
                component: () => import('./financeiro/contas_bancarias/ContasBancarias.vue')
            },
            {
                path: '/contas_fluxo_caixa',
                name: 'contas_fluxo_caixa',
                component: () => import('./financeiro/fluxo_de_caixa/ContasFluxoCaixa.vue')
            },
            {
                path: '/lancamentos_fluxo_caixa',
                name: 'lancamentos_fluxo_caixa',
                component: () => import('./financeiro/fluxo_de_caixa/LancamentosFluxoCaixa.vue')
            },
            {
                path: '/pagar_receber',
                name: 'pagar_receber',
                component: () => import('./financeiro/pagar_receber/PagarReceber.vue')
            },
            {
                path: '/baixas_pagar_receber',
                name: 'baixas_pagar_receber',
                component: () => import('./financeiro/pagar_receber/BaixaPagarReceber.vue')
            },
            {
                path: '/ordemPagamento',
                name: 'ordemPagamento',
                component: () => import('./financeiro/pagar_receber/OrdemPagamento.vue')
            },
            {
                path: '/ib_configuracoes',
                name: 'ib_configuracoes',
                component: () => import('./utilitarios/integracao_bancaria/ConfiguracoesIntegracaoBancaria.vue')
            },
            {
                path: '/ib_status_pagamento',
                name: 'ib_status_pagamentp',
                component: () => import('./utilitarios/integracao_bancaria/StatusPagamento.vue')
            },
            {
                path: '/ib_status_recebimento',
                name: 'ib_status_recebimento',
                component: () => import('./utilitarios/integracao_bancaria/StatusRecebimento.vue')
            },
            {
                path: '/simulador_venda',
                name: 'simulador_venda',
                component: () => import('./cadastros/venda/Venda.vue')
            },
        ]
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('./auth/Login.vue')
    }
];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

router.beforeEach(async (to) => {
    const publicPages = ['/login'];
    const authRequired = !publicPages.includes(to.path);

    if (authRequired) {
        try {
            await Auth.currentAuthenticatedUser();
        } catch {
            // Caso não logado vai para o login
            return '/login';
        }
    }

    return true;
});

export default router;
