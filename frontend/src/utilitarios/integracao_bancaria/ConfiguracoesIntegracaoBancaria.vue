<template>
	<div class="grid">
		<div class="col-12">
			<div class="card">
				<Toast/>
				<Toolbar class="mb-4">
					<template v-slot:start>
						<div class="my-2">
							<Button label="Confirmar Todas" icon="pi pi-check" class="p-button-success mr-2" @click="configuraComoConfirmado" />
							<Button label="Rejeitar Todas" icon="pi pi-trash" class="p-button-danger mr-2" @click="configuraComoRejeitado" />
							<Button label="Desligar Processamento" icon="pi pi-times" class="p-button-warning mr-2" @click="configuraComoDesligado" />
						</div>
					</template>
				</Toolbar>
				<h1>Situação: <b>{{situacaoAtual}}</b></h1>

				<p>
					Observação POC: Essa tela mostra a configuração atual de processamento da integração bancária. Se confirmar todas,
					a integração irá confirmar todos os pagamentos e recebimento. Rejeitar irá rejeitar todos.
					Desligar, desliga o processamento de pagamentos e recebimentos.
				</p>
			</div>
		</div>
	</div>

</template>

<script>
const API_RESOURCE = '/integracao/configuracao/statuspadraoproc';

//Adicionada no cliente, pois essa tela é utilizada apenas para fins de testes.
const AUTH_CONFIG = {
	headers: {
		'Authorization' : 'Bearer b843de44e8ef7a67974ecfeefed6479eda4a84bf'
	}
};

export default {
	data() {
		return {
			situacaoAtual: '********'
		}
	},
	
	mounted() {
		this.buscarSituacaoProcessamento();
	},

	methods: {
		configuraComoConfirmado() {
			this.axios.get(API_RESOURCE+'/confirmado', AUTH_CONFIG)
				.then(() => {
					this.$toast.add({severity:'success', summary: 'Sucesso', detail: 'Alterado para Confirmado.', life: 3000});
					this.buscarSituacaoProcessamento();
				});
		},

		configuraComoRejeitado() {
			this.axios.get(API_RESOURCE+'/rejeitado', AUTH_CONFIG)
				.then(() => {
					this.$toast.add({severity:'success', summary: 'Sucesso', detail: 'Alterado para Rejeitado.', life: 3000});
					this.buscarSituacaoProcessamento();
				});
		},


		configuraComoDesligado() {
			this.axios.get(API_RESOURCE+'/desligado', AUTH_CONFIG)
				.then(() => {
					this.$toast.add({severity:'success', summary: 'Sucesso', detail: 'Alterado para Desligado.', life: 3000});
					this.buscarSituacaoProcessamento();
				});
		},

		buscarSituacaoProcessamento() {
			this.axios.get(API_RESOURCE, AUTH_CONFIG)
				.then(data => {
					if(data.data.status == 'EM_PROCESSAMENTO') {
						this.situacaoAtual = 'DESLIGADO';
					} else {
						this.situacaoAtual = data.data.status;
					}
				});
		}
	}
}
</script>

<style scoped lang="scss">
@import '@/assets/demo/badges.scss';
</style>
