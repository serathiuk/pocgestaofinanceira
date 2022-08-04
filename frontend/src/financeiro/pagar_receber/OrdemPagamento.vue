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

				<DataTable ref="dt" :value="ordensPagamento" dataKey="id" :paginator="true" :rows="10"
							paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown" :rowsPerPageOptions="[5,10,25]"
							currentPageReportTemplate="Mostrando {first} até {last} de {totalRecords} ordens" responsiveLayout="scroll">
					<template #header>
						<div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
							<h5 class="m-0">Ordem de Pagamento</h5>
						</div>
					</template>

					<Column field="id" header="Identificador" :sortable="true" headerStyle="width:40%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Identificador</span>
							{{slotProps.data.id}}
						</template>
					</Column>

					<Column field="descricaoPagarReceber" header="Descrição do Pagar/Receber" :sortable="true" headerStyle="width:40%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Descrição do Pagar/Receber</span>
							{{slotProps.data.descricaoPagarReceber}}
						</template>
					</Column>

					<Column field="valorTotal" header="Valor Total" :sortable="true" headerStyle="width:20%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Valor da Total</span>
							{{slotProps.data.valorTotal}}
						</template>
					</Column>

					<Column field="status" header="Situação" :sortable="true" headerStyle="width:40%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Situação</span>
							{{slotProps.data.status}}
						</template>
					</Column>

					<Column headerStyle="min-width:10rem;">
						<template #body="slotProps">
							<Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editarOrdemPagamento(slotProps.data)" />
						</template>
					</Column>
				</DataTable>

				<Dialog v-model:visible="ordemPagamentoDialog" :style="{width: '450px'}" header="Ordem de Pagamento" :modal="true" class="p-fluid">

					<div class="field" v-if="!novoItem">
						<label for="id">Identificador</label>
						<InputText v-model="ordemPagamento.id" disabled="true" />
						<small class="p-invalid" v-if="submitted && !ordemPagamento.valorTotal">Valor Total é obrigatório.</small>
					</div>

					<div class="field" v-if="novoItem">
						<label for="idPagarReceber">Pagar/Receber</label>
						<Dropdown v-model="ordemPagamento.idPagarReceber" :options="titulosPagarReceber" optionLabel="descricaoLancamento" optionValue="id" placeholder="Escolha a Pagar/Receber" />
						<small class="p-invalid" v-if="submitted && !ordemPagamento.idPagarReceber">Pagar/Receber é obrigatória.</small>
					</div>

					<div class="field" v-if="!novoItem">
						Pagar/Receber 
						<br>
						<br>
						<i>{{ordemPagamento.descricaoPagarReceber}}</i>
					</div>

					<div class="field">
						<label for="idFilialPagamento">Filial de Pagamento</label>
						<Dropdown v-model="ordemPagamento.idFilialPagamento" :options="filiais" optionLabel="nome" optionValue="id" placeholder="Escolha a Filial" />
						<small class="p-invalid" v-if="submitted && !ordemPagamento.idFilialPagamento">Filial de Pagamento é obrigatória.</small>
					</div>

					<div class="field">
						<label for="idContaBancaria">Conta Bancária</label>
						<Dropdown v-model="ordemPagamento.idContaBancaria" :options="contasBancarias" optionLabel="nome" optionValue="id" placeholder="Escolha a Pagar/Receber" />
						<small class="p-invalid" v-if="submitted && !ordemPagamento.idContaBancaria">Conta Bancária é obrigatória.</small>
					</div>

					<div class="field">
						<label for="valorTotal">Valor Total</label>
						<InputNumber v-model="ordemPagamento.valorTotal" mode="currency" currency="BRL" locale="pt-BR" />
						<small class="p-invalid" v-if="submitted && !ordemPagamento.valorTotal">Valor Total é obrigatório.</small>
					</div>

					<template #footer>
						<Button label="Cancelar" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
						<Button label="Salvar" icon="pi pi-check" class="p-button-text" @click="salvarOrdemPagamento" v-if="novoItem" />
					</template>
				</Dialog>
			</div>
		</div>
	</div>

</template>

<script>
const API_RESOURCE = '/financeiro/ordemPagamento';

export default {
	data() {
		return {
			ordensPagamento: null,
			ordemPagamentoDialog: false,
			ordemPagamento: {},
			submitted: false,
			filiais: [],
			titulosPagarReceber: [],
			contasFluxoCaixa: [],
			contasBancarias: [],
			novoItem: true
		}
	},
	
	mounted() {
		this.atualizarOrdensPagamento();
		this.atualizarFiliais();
		this.atualizaPagarReceber();
		this.atualizaContasBancarias();
	},

	methods: {

		atualizarOrdensPagamento() {
			this.axios.get(API_RESOURCE)
				.then(resp => this.ordensPagamento = resp.data.content);
		},

		atualizarFiliais() {
			this.axios.get('/cadastro/filial')
				.then(resp => this.filiais = resp.data.content);
		},

		atualizaPagarReceber() {
			this.axios.get('/financeiro/pagarReceber?tipoOperacao=PAGAR')
				.then(resp => this.titulosPagarReceber = resp.data.content);
		},

		atualizaContasBancarias() {
			this.axios.get('/financeiro/conta_bancaria')
				.then(resp => this.contasBancarias = resp.data.content);
		},

		openNew() {
			this.ordemPagamento = {};
			this.submitted = false;
			this.novoItem = true;
			this.ordemPagamentoDialog = true;
		},

		hideDialog() {
			this.ordemPagamentoDialog = false;
			this.submitted = false;
		},

		salvarOrdemPagamento() {
			this.submitted = true;
			
			const ordemPagamento = {
				id: this.ordemPagamento.id,
				idFilialPagamento: this.ordemPagamento.idFilialPagamento,
				idPagarReceber: this.ordemPagamento.idPagarReceber,
				valorTotal: this.ordemPagamento.valorTotal,
				idContaBancaria: this.ordemPagamento.idContaBancaria,
				observacoes: this.ordemPagamento.observacoes
			};

			console.log(ordemPagamento);

			const header = {
				'Content-Type': 'application/json'
			}

			const onSucesso = res => {
				if(res.status == 200) {
					this.$toast.add({severity:'success', summary: 'Sucesso', detail: 'Ordem de Pagamento salva com sucesso', life: 3000});
					this.ordemPagamentoDialog = false;
					this.ordemPagamento = {};
					this.atualizarOrdensPagamento();
				} else {
					this.$toast.add({severity:'warning', summary: 'Erro', detail: 'Erro ao salvar Ordem de Pagamento', life: 3000});
				}
			}

			if(ordemPagamento.id) {
				this.axios.put(API_RESOURCE+'/'+ordemPagamento.id, ordemPagamento, header)
					.then(onSucesso);
			} else {
				this.axios.post(API_RESOURCE, ordemPagamento, header)
					.then(onSucesso);
			}
		},

		editarOrdemPagamento(ordemPagamento) {
			this.axios.get(API_RESOURCE+'/'+ordemPagamento.id)
				.then(resp => {
					this.ordemPagamento = resp.data;
					this.novoItem = false;
					this.ordemPagamentoDialog = true;
				});
			
		},

	}
}
</script>

<style scoped lang="scss">
@import '@/assets/demo/badges.scss';
</style>
