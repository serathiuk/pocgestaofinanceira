<template>
    <div class="surface-0 flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden">
        <div class="grid justify-content-center p-2 lg:p-0" style="min-width:80%">
            <div class="col-12 xl:col-6" style="border-radius:56px; padding:0.3rem;">
            <authenticator>
                <template v-slot="{ user, signOut }">
                    <h1>Olá {{ user.username }}!</h1>
                    <h3>
                        <router-link to="/">Ir para o ERP</router-link>
                    </h3>
                    <button @click="signOut">Sair</button>
                </template>
            </authenticator>
            </div>
        </div>
    </div>
</template>

<script setup>
  import { Authenticator } from '@aws-amplify/ui-vue';
  import '@aws-amplify/ui-vue/styles.css';
</script>

<style scoped>
.pi-eye {
    transform:scale(1.6);
    margin-right: 1rem;
}

.pi-eye-slash {
    transform:scale(1.6);
    margin-right: 1rem;
}
</style>