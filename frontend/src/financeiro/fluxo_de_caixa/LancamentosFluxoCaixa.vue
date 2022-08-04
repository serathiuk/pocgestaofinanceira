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

				<DataTable ref="dt" :value="lancamentosFluxoCaixa" dataKey="id" :paginator="true" :rows="10"
							paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown" :rowsPerPageOptions="[5,10,25]"
							currentPageReportTemplate="Mostrando {first} até {last} de {totalRecords} lançamentos" responsiveLayout="scroll">
					<template #header>
						<div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
							<h5 class="m-0">Lançamentos Fluxo de Caixa</h5>
						</div>
					</template>

					<Column field="descricaoLancamento" header="Descrição do Lançamento" :sortable="true" headerStyle="width:30%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Descrição do Lançamento</span>
							{{slotProps.data.descricaoLancamento}}
						</template>
					</Column>

					<Column field="dataHoraOperacao" header="Data/Hora Operação" :sortable="true" headerStyle="width:20%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Data/Hora Operação</span>
							{{formatDate(slotProps.data.dataHoraOperacao)}}
						</template>
					</Column>

					<Column field="valorOperacao" header="Valor" :sortable="true" headerStyle="width:20%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Valor</span>
							{{slotProps.data.valorOperacao}}
						</template>
					</Column>

					<Column field="tipoLancamento" header="Tipo" :sortable="true" headerStyle="width:20%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Valor</span>
							{{slotProps.data.tipoLancamento}}
						</template>
					</Column>

					<Column headerStyle="min-width:10rem;">
						<template #body="slotProps">
							<Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editarLancamentoFluxoCaixa(slotProps.data)" />
							<Button icon="pi pi-trash" class="p-button-rounded p-button-warning mt-2" @click="confirmarRemocaoLancamentoFluxoCaixa(slotProps.data)" v-if="verificaPermissao(slotProps.data)"/>
						</template>
					</Column>
				</DataTable>

				<Dialog v-model:visible="lancamentoFluxoCaixaDialog" :style="{width: '450px'}" header="Lançamento Fluxo de Caixa" :modal="true" class="p-fluid">

					<div class="field">
						<label for="idFilial">Filial</label>
						<Dropdown v-model="lancamentoFluxoCaixa.idFilial" :options="filiais" optionLabel="nome" optionValue="id" placeholder="Escolha a Filial" />
						<small class="p-invalid" v-if="submitted && !lancamentoFluxoCaixa.idFilial">Filial é obrigatória.</small>
					</div>

					<div class="field">
						<label for="contaId">Conta Bancária</label>
						<Dropdown v-model="lancamentoFluxoCaixa.contaId" :options="contasBancarias" optionLabel="nome" optionValue="id" placeholder="Escolha a Conta Bancária" />
						<small class="p-invalid" v-if="submitted && !lancamentoFluxoCaixa.contaId">Conta Bancária é obrigatória.</small>
					</div>

					<div class="field">
						<label for="descricaoLancamento">Descrição do Lançamento</label>
						<InputText id="descricaoLancamento" v-model.trim="lancamentoFluxoCaixa.descricaoLancamento" required="true" autofocus :class="{'p-invalid': submitted && !lancamentoFluxoCaixa.descricaoLancamento}" />
						<small class="p-invalid" v-if="submitted && !lancamentoFluxoCaixa.descricaoLancamento">Nome é obrigatório.</small>
					</div>

					<div class="field">
						<label for="contaFluxoCaixaId">Conta Fluxo de Caixa</label>
						<Dropdown v-model="lancamentoFluxoCaixa.contaFluxoCaixaId" :options="contasFluxoCaixa" optionLabel="nome" optionValue="id" placeholder="Escolha a Conta Fluxo de Caixa" />
						<small class="p-invalid" v-if="submitted && !lancamentoFluxoCaixa.contaFluxoCaixaId">Conta Fluxo de Caixa é obrigatória.</small>
					</div>

					<div class="field">
						<label class="mb-3">Tipo</label>
						<div class="formgrid grid">
							<div class="field-radiobutton col-6">
								<RadioButton id="DEBITO" name="tipoLancamento" value="DEBITO" v-model="lancamentoFluxoCaixa.tipoLancamento" />
								<label for="DEBITO">Débito</label>
							</div>
							<div class="field-radiobutton col-6">
								<RadioButton id="CREDITO" name="tipoLancamento" value="CREDITO" v-model="lancamentoFluxoCaixa.tipoLancamento" />
								<label for="CREDITO">Crédito</label>
							</div>
						</div>
					</div>

					<div class="field">
						<label for="dataHoraOperacao">Data da Operação</label>
						<Calendar v-model="lancamentoFluxoCaixa.dataHoraOperacao" dateFormat="dd/mm/yy" />
						<small class="p-invalid" v-if="submitted && !lancamentoFluxoCaixa.dataHoraOperacao">Data/Hora é obrigatória.</small>
					</div>

					<div class="field">
						<label for="valorOperacao">Valor da Operação</label>
						<InputNumber v-model="lancamentoFluxoCaixa.valorOperacao" mode="currency" currency="BRL" locale="pt-BR" />
						<small class="p-invalid" v-if="submitted && !lancamentoFluxoCaixa.valorOperacao">Valor é obrigatório.</small>
					</div>

					<template #footer>
						<Button label="Cancelar" icon="pi pi-times" class="p-button-text" @click="hideDialog"/>
						<Button label="Salvar" icon="pi pi-check" class="p-button-text" @click="salvarLancamentoFluxoCaixa" v-if="verificaPermissao(lancamentoFluxoCaixa)"/>
					</template>
				</Dialog>

				<Dialog v-model:visible="removerLancamentoFluxoCaixaDialog" :style="{width: '450px'}" header="Confirm" :modal="true">
					<div class="flex align-items-center justify-content-center">
						<i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
						<span v-if="lancamentoFluxoCaixa">Deseja remover remover o Lançamento <b>{{lancamentoFluxoCaixa.nome}}</b>?</span>
					</div>
					<template #footer>
						<Button label="No" icon="pi pi-times" class="p-button-text" @click="removerLancamentoFluxoCaixaDialog = false"/>
						<Button label="Yes" icon="pi pi-check" class="p-button-text" @click="removerLancamentoFluxoCaixa" />
					</template>
				</Dialog>
			</div>
		</div>
	</div>

</template>

<script>
const API_RESOURCE = '/financeiro/lancamento_fluxo_caixa';

export default {
	data() {
		return {
			lancamentosFluxoCaixa: null,
			lancamentoFluxoCaixaDialog: false,
			removerLancamentoFluxoCaixaDialog: false,
			lancamentoFluxoCaixa: {},
			submitted: false,
			filiais: [],
			contasBancarias: [],
			contasFluxoCaixa: [],
		}
	},
	
	mounted() {
		this.atualizarLancamentosFluxoCaixa();
		this.atualizarFiliais();
		this.atualizaContasBancarias();
		this.atualizaContasFluxoCaixa();
	},

	methods: {

        formatDate(value) {
            return new Date(value).toLocaleDateString('pt-BR', {
                day: '2-digit',
                month: '2-digit',
                year: 'numeric',
            });
        },

		atualizarLancamentosFluxoCaixa() {
			this.axios.get(API_RESOURCE)
				.then(resp => this.lancamentosFluxoCaixa = resp.data.content);
		},

		atualizarFiliais() {
			this.axios.get('/cadastro/filial')
				.then(resp => this.filiais = resp.data.content);
		},

		atualizaContasBancarias() {
			this.axios.get('/financeiro/conta_bancaria')
				.then(resp => this.contasBancarias = resp.data.content);
		},

		atualizaContasFluxoCaixa() {
			this.axios.get('/financeiro/conta_fluxo_caixa')
				.then(resp => this.contasFluxoCaixa = resp.data.content);
		},

		openNew() {
			this.lancamentoFluxoCaixa = {};
			this.submitted = false;
			this.lancamentoFluxoCaixaDialog = true;
		},

		hideDialog() {
			this.lancamentoFluxoCaixaDialog = false;
			this.submitted = false;
		},

		salvarLancamentoFluxoCaixa() {
			this.submitted = true;
			
			const lancamentoFluxoCaixa = {
				id: this.lancamentoFluxoCaixa.id,
				idFilial: this.lancamentoFluxoCaixa.idFilial,
				contaId: this.lancamentoFluxoCaixa.contaId,
				descricaoLancamento: this.lancamentoFluxoCaixa.descricaoLancamento,
				contaFluxoCaixaId: this.lancamentoFluxoCaixa.contaFluxoCaixaId,
				tipoLancamento: this.lancamentoFluxoCaixa.tipoLancamento,
				dataHoraOperacao: this.lancamentoFluxoCaixa.dataHoraOperacao,
				valorOperacao: this.lancamentoFluxoCaixa.valorOperacao,
			};

			console.log(lancamentoFluxoCaixa);

			const header = {
				'Content-Type': 'application/json'
			}

			const onSucesso = res => {
				if(res.status == 200) {
					this.$toast.add({severity:'success', summary: 'Sucesso', detail: 'Lancamento Fluxo de Caixa salvo com sucesso', life: 3000});
					this.lancamentoFluxoCaixaDialog = false;
					this.lancamentoFluxoCaixa = {};
					this.atualizarLancamentosFluxoCaixa();
				} else {
					this.$toast.add({severity:'warning', summary: 'Erro', detail: 'Erro ao salvar Lancamento Fluxo de Caixa', life: 3000});
				}
			}

			if(lancamentoFluxoCaixa.id) {
				this.axios.put(API_RESOURCE+'/'+lancamentoFluxoCaixa.id, lancamentoFluxoCaixa, header)
					.then(onSucesso);
			} else {
				this.axios.post(API_RESOURCE, lancamentoFluxoCaixa, header)
					.then(onSucesso);
			}
		},

		editarLancamentoFluxoCaixa(lancamentoFluxoCaixa) {
			this.axios.get(API_RESOURCE+'/'+lancamentoFluxoCaixa.id)
				.then(resp => {
					this.lancamentoFluxoCaixa = resp.data;
					this.lancamentoFluxoCaixaDialog = true;
				});
			
		},

		confirmarRemocaoLancamentoFluxoCaixa(lancamentoFluxoCaixa) {
			this.lancamentoFluxoCaixa = lancamentoFluxoCaixa;
			this.removerLancamentoFluxoCaixaDialog = true;
		},

		removerLancamentoFluxoCaixa() {
			this.axios.delete(API_RESOURCE+'/'+this.lancamentoFluxoCaixa.id)
				.then(() => {
					this.removerLancamentoFluxoCaixaDialog = false;
					this.lancamentoFluxoCaixa = {};
					this.$toast.add({severity:'success', summary: 'Sucesso', detail: 'Lancamento Fluxo de Caixa removido com sucesso.', life: 3000});
					this.atualizarLancamentosFluxoCaixa();
				})
				.catch((error) => {
					this.removerLancamentoFluxoCaixaDialog = false;
					this.lancamentoFluxoCaixa = {};
					this.$toast.add({severity:'error', summary: 'Error', detail: error.response.data.message, life: 3000});
				});
		},

		verificaPermissao(lancamentoFluxoCaixa) {
			return lancamentoFluxoCaixa.id == null || lancamentoFluxoCaixa.idOrigemOperacao == null;
		}

	}
}
</script>

<style scoped lang="scss">
@import '@/assets/demo/badges.scss';
</style>
