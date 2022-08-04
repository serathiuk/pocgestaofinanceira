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

				<DataTable ref="dt" :value="baixasPagarReceber" dataKey="id" :paginator="true" :rows="10"
							paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown" :rowsPerPageOptions="[5,10,25]"
							currentPageReportTemplate="Mostrando {first} até {last} de {totalRecords} baixas" responsiveLayout="scroll">
					<template #header>
						<div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
							<h5 class="m-0">Baixa de Pagar e Receber</h5>
						</div>
					</template>

					<Column field="descricaoPagarReceber" header="Descrição do Pagar/Receber" :sortable="true" headerStyle="width:30%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Descrição do Pagar/Receber</span>
							{{slotProps.data.descricaoPagarReceber}}
						</template>
					</Column>
					
					<Column field="tipoOperacaoPagarReceber" header="Tipo" :sortable="true" headerStyle="width:10%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Tipo</span>
							{{slotProps.data.tipoOperacaoPagarReceber}}
						</template>
					</Column>

					<Column field="nomeContaBancaria" header="Conta Bancária" :sortable="true" headerStyle="width:30%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Conta Bancária</span>
							{{slotProps.data.nomeContaBancaria}}
						</template>
					</Column>

					<Column field="dataHoraBaixa" header="Data da Baixa" :sortable="true" headerStyle="width:10%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Data da Baixa</span>
							{{formatDate(slotProps.data.dataHoraBaixa)}}
						</template>
					</Column>

					<Column field="valorBaixa" header="Valor da Baixa" :sortable="true" headerStyle="width:10%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Valor da Baixa</span>
							{{slotProps.data.valorBaixa}}
						</template>
					</Column>

					<Column field="situacaoBaixa" header="Situação" :sortable="true" headerStyle="width:10%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Situação</span>
							{{slotProps.data.situacaoBaixa}}
						</template>
					</Column>

					<Column headerStyle="min-width:10rem;">
						<template #body="slotProps">
							<Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editarBaixaPagarReceber(slotProps.data)" />
							<Button icon="pi pi-trash" class="p-button-rounded p-button-warning mt-2" @click="confirmarRemocaoBaixaPagarReceber(slotProps.data)" v-if="verificaPermissao(slotProps.data)" />
						</template>
					</Column>
				</DataTable>

				<Dialog v-model:visible="baixaPagarReceberDialog" :style="{width: '450px'}" header="Pagar e Receber" :modal="true" class="p-fluid">

					<div class="field">
						<label for="idFilial">Filial de Pagamento</label>
						<Dropdown v-model="baixaPagarReceber.idFilial" :options="filiais" optionLabel="nome" optionValue="id" placeholder="Escolha a Filial" />
						<small class="p-invalid" v-if="submitted && !baixaPagarReceber.idFilial">Filial de Pagamento é obrigatória.</small>
					</div>

					<div class="field" v-if="baixaPagarReceber.id == null">
                        <label for="idPagarReceber">Pagar/Receber</label>
                        <Dropdown v-model="baixaPagarReceber.idPagarReceber" :options="titulosPagarReceber" optionLabel="descricaoLancamento" optionValue="id" placeholder="Escolha a Pagar/Receber" />
                        <small class="p-invalid" v-if="submitted && !baixaPagarReceber.idPagarReceber">Pagar/Receber é obrigatória.</small>
					</div>

					<div class="field" v-if="baixaPagarReceber.id != null">
						Pagar/Receber 
						<br>
						<br>
						<i>{{baixaPagarReceber.descricaoPagarReceber}}</i>
					</div>

					<div class="field">
						<label for="idContaBancaria">Conta Bancária</label>
						<Dropdown v-model="baixaPagarReceber.idContaBancaria" :options="contasBancarias" optionLabel="nome" optionValue="id" placeholder="Escolha a Pagar/Receber" />
						<small class="p-invalid" v-if="submitted && !baixaPagarReceber.idContaBancaria">Conta Bancária é obrigatória.</small>
					</div>

					<div class="field">
						<label for="dataHoraBaixa">Data da Baixa</label>
						<Calendar v-model="baixaPagarReceber.dataHoraBaixa" dateFormat="dd/mm/yy" />
						<small class="p-invalid" v-if="submitted && !baixaPagarReceber.dataHoraBaixa">Data da Baixa é obrigatória.</small>
					</div>

					<div class="field">
						<label for="valorBaixa">Valor da Baixa</label>
						<InputNumber v-model="baixaPagarReceber.valorBaixa" mode="currency" currency="BRL" locale="pt-BR" />
						<small class="p-invalid" v-if="submitted && !baixaPagarReceber.valorBaixa">Valor da Baixa é obrigatório.</small>
					</div>

					<div class="field">
						<label for="idContaFluxoCaixa">Conta Fluxo de Caixa</label>
						<Dropdown v-model="baixaPagarReceber.idContaFluxoCaixa" :options="contasFluxoCaixa" optionLabel="nome" optionValue="id" placeholder="Escolha a Conta Fluxo de Caixa" />
						<small class="p-invalid" v-if="submitted && !baixaPagarReceber.idContaFluxoCaixa">Conta Fluxo de Caixa é obrigatória.</small>
					</div>

					<div class="field">
						<label for="observacoes">Observações</label>
						<InputText id="observacoes" v-model.trim="baixaPagarReceber.observacoes" required="true" autofocus :class="{'p-invalid': submitted && !baixaPagarReceber.observacoes}" />
						<small class="p-invalid" v-if="submitted && !baixaPagarReceber.observacoes">Observações é obrigatório.</small>
					</div>

					<template #footer>
						<Button label="Cancelar" icon="pi pi-times" class="p-button-text" @click="hideDialog"/>
						<Button label="Salvar" icon="pi pi-check" class="p-button-text" @click="salvarBaixaPagarReceber" v-if="verificaPermissao(baixaPagarReceber)" />
					</template>
				</Dialog>

				<Dialog v-model:visible="removerBaixaPagarReceberDialog" :style="{width: '450px'}" header="Confirm" :modal="true">
					<div class="flex align-items-center justify-content-center">
						<i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
						<span v-if="baixaPagarReceber">Deseja remover remover o Lançamento <b>{{baixaPagarReceber.nome}}</b>?</span>
					</div>
					<template #footer>
						<Button label="No" icon="pi pi-times" class="p-button-text" @click="removerBaixaPagarReceberDialog = false"/>
						<Button label="Yes" icon="pi pi-check" class="p-button-text" @click="removerBaixaPagarReceber" />
					</template>
				</Dialog>
			</div>
		</div>
	</div>

</template>

<script>
const API_RESOURCE = '/financeiro/baixaPagarReceber';

export default {
	data() {
		return {
			baixasPagarReceber: null,
			baixaPagarReceberDialog: false,
			removerBaixaPagarReceberDialog: false,
			baixaPagarReceber: {},
			submitted: false,
			filiais: [],
			titulosPagarReceber: [],
			contasFluxoCaixa: [],
			contasBancarias: [],
		}
	},
	
	mounted() {
		this.atualizarBaixasPagarReceber();
		this.atualizarFiliais();
		this.atualizaPagarReceber();
		this.atualizaContasFluxoCaixa();
		this.atualizaContasBancarias();
	},

	methods: {

        formatDate(value) {
            return new Date(value).toLocaleDateString('pt-BR', {
                day: '2-digit',
                month: '2-digit',
                year: 'numeric',
            });
        },

		atualizarBaixasPagarReceber() {
			this.axios.get(API_RESOURCE)
				.then(resp => this.baixasPagarReceber = resp.data.content);
		},

		atualizarFiliais() {
			this.axios.get('/cadastro/filial')
				.then(resp => this.filiais = resp.data.content);
		},

		atualizaPagarReceber() {
			this.axios.get('/financeiro/pagarReceber?situacao=ABERTO')
				.then(resp => this.titulosPagarReceber = resp.data.content);
		},

		atualizaContasFluxoCaixa() {
			this.axios.get('/financeiro/conta_fluxo_caixa')
				.then(resp => this.contasFluxoCaixa = resp.data.content);
		},

		atualizaContasBancarias() {
			this.axios.get('/financeiro/conta_bancaria')
				.then(resp => this.contasBancarias = resp.data.content);
		},

		openNew() {
			this.baixaPagarReceber = {};
			this.submitted = false;
			this.baixaPagarReceberDialog = true;
			this.atualizaPagarReceber();
		},

		hideDialog() {
			this.baixaPagarReceberDialog = false;
			this.submitted = false;
		},

		salvarBaixaPagarReceber() {
			this.submitted = true;
			
			const baixaPagarReceber = {
				id: this.baixaPagarReceber.id,
				idFilial: this.baixaPagarReceber.idFilial,
				idPagarReceber: this.baixaPagarReceber.idPagarReceber,
				dataHoraBaixa: this.baixaPagarReceber.dataHoraBaixa,
				valorBaixa: this.baixaPagarReceber.valorBaixa,
				idContaBancaria: this.baixaPagarReceber.idContaBancaria,
				idContaFluxoCaixa: this.baixaPagarReceber.idContaFluxoCaixa,
				observacoes: this.baixaPagarReceber.observacoes,
				situacaoBaixa: 'PENDENTE',
				valorDesconto: 0,
				valorJuros: 0
			};

			console.log(baixaPagarReceber);

			const header = {
				'Content-Type': 'application/json'
			}

			const onSucesso = res => {
				if(res.status == 200) {
					this.$toast.add({severity:'success', summary: 'Sucesso', detail: 'Baixa de Pagar e Receber salva com sucesso', life: 3000});
					this.baixaPagarReceberDialog = false;
					this.baixaPagarReceber = {};
					this.atualizarBaixasPagarReceber();
				} else {
					this.$toast.add({severity:'warning', summary: 'Erro', detail: 'Erro ao salvar Baixa de Pagar e Receber', life: 3000});
				}
			}

			if(baixaPagarReceber.id) {
				this.axios.put(API_RESOURCE+'/'+baixaPagarReceber.id, baixaPagarReceber, header)
					.then(onSucesso);
			} else {
				this.axios.post(API_RESOURCE, baixaPagarReceber, header)
					.then(onSucesso);
			}
		},

		editarBaixaPagarReceber(baixaPagarReceber) {
			this.axios.get(API_RESOURCE+'/'+baixaPagarReceber.id)
				.then(resp => {
					this.baixaPagarReceber = resp.data;
					this.baixaPagarReceberDialog = true;
				});
			
		},

		confirmarRemocaoBaixaPagarReceber(baixaPagarReceber) {
			this.baixaPagarReceber = baixaPagarReceber;
			this.removerBaixaPagarReceberDialog = true;
		},

		removerBaixaPagarReceber() {
			this.axios.delete(API_RESOURCE+'/'+this.baixaPagarReceber.id)
				.then(() => {
					this.removerBaixaPagarReceberDialog = false;
					this.baixaPagarReceber = {};
					this.$toast.add({severity:'success', summary: 'Sucesso', detail: 'Baixa de Pagar e Receber removida com sucesso.', life: 3000});
					this.atualizarBaixasPagarReceber();
				})
				.catch((error) => {
					this.removerBaixaPagarReceberDialog = false;
					this.baixaPagarReceber = {};
					this.$toast.add({severity:'error', summary: 'Error', detail: error.response.data.message, life: 3000});
				});
		},

		verificaPermissao(baixaPagarReceber) {
			return baixaPagarReceber.id == null || baixaPagarReceber.situacaoBaixa == 'PENDENTE';
		}
	}
}
</script>

<style scoped lang="scss">
@import '@/assets/demo/badges.scss';
</style>
