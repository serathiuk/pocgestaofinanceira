<template>
	<div class="grid">
		<div class="col-12">
			<div class="card">
				<Toast/>
				<Toolbar class="mb-4">
					<template v-slot:start>
						<div class="my-2">
							<Button label="Novo" icon="pi pi-plus" class="p-button-success mr-2" @click="openNew" />
						</div>
					</template>
				</Toolbar>

				<DataTable ref="dt" :value="contasFluxoCaixa" dataKey="id" :paginator="true" :rows="10"
							paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown" :rowsPerPageOptions="[5,10,25]"
							currentPageReportTemplate="Mostrando {first} até {last} de {totalRecords} contas fluxo de caixa" responsiveLayout="scroll">
					<template #header>
						<div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
							<h5 class="m-0">Contas Fluxo de Caixa</h5>
						</div>
					</template>

					<Column field="nome" header="Nome" :sortable="true" headerStyle="width:100%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Bome</span>
							{{slotProps.data.nome}}
						</template>
					</Column>

					<Column headerStyle="min-width:10rem;">
						<template #body="slotProps">
							<Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editarContaFluxoCaixa(slotProps.data)" />
						</template>
					</Column>
				</DataTable>

				<Dialog v-model:visible="contaFluxoCaixaDialog" :style="{width: '450px'}" header="ContaFluxoCaixa" :modal="true" class="p-fluid">
					<div class="field">
						<label for="nome">Nome</label>
						<InputText id="nome" v-model.trim="contaFluxoCaixa.nome" required="true" autofocus :class="{'p-invalid': submitted && !contaFluxoCaixa.nome}" />
						<small class="p-invalid" v-if="submitted && !contaFluxoCaixa.nome">Nome é obrigatório.</small>
					</div>

					<template #footer>
						<Button label="Cancelar" icon="pi pi-times" class="p-button-text" @click="hideDialog"/>
						<Button label="Salvar" icon="pi pi-check" class="p-button-text" @click="salvarContaFluxoCaixa" />
					</template>
				</Dialog>
			</div>
		</div>
	</div>

</template>

<script>
const API_RESOURCE = '/financeiro/conta_fluxo_caixa';

export default {
	data() {
		return {
			contasFluxoCaixa: null,
			contaFluxoCaixaDialog: false,
			contaFluxoCaixa: {},
			submitted: false
		}
	},
	
	mounted() {
		this.atualizarContasFluxoCaixa();
	},

	methods: {

		atualizarContasFluxoCaixa() {
			this.axios.get(API_RESOURCE)
				.then(resp => this.contasFluxoCaixa = resp.data.content);
		},

		openNew() {
			this.contaFluxoCaixa = {};
			this.submitted = false;
			this.contaFluxoCaixaDialog = true;
		},

		hideDialog() {
			this.contaFluxoCaixaDialog = false;
			this.submitted = false;
		},

		salvarContaFluxoCaixa() {
			this.submitted = true;
			
			const contaFluxoCaixa = {
				id: this.contaFluxoCaixa.id,
				nome: this.contaFluxoCaixa.nome,
				tipoConta: this.contaFluxoCaixa.tipoConta
			};

			const header = {
				'Content-Type': 'application/json'
			}

			const onSucesso = res => {
				if(res.status == 200) {
					this.$toast.add({severity:'success', summary: 'Sucesso', detail: 'Conta Fluxo de Caixa salva com sucesso', life: 3000});
					this.contaFluxoCaixaDialog = false;
					this.contaFluxoCaixa = {};
					this.atualizarContasFluxoCaixa();
				} else {
					this.$toast.add({severity:'warning', summary: 'Erro', detail: 'Erro ao salvar Conta Fluxo de Caixa', life: 3000});
				}
			}

			if(contaFluxoCaixa.id) {
				this.axios.put(API_RESOURCE+'/'+contaFluxoCaixa.id, contaFluxoCaixa, header)
					.then(onSucesso);
			} else {
				this.axios.post(API_RESOURCE, contaFluxoCaixa, header)
					.then(onSucesso);
			}
		},

		editarContaFluxoCaixa(contaFluxoCaixa) {
			this.axios.get(API_RESOURCE+'/'+contaFluxoCaixa.id)
				.then(resp => {
					this.contaFluxoCaixa = resp.data;
					this.contaFluxoCaixaDialog = true;
				});
			
		}
	}
}
</script>

<style scoped lang="scss">
@import '@/assets/demo/badges.scss';
</style>
