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

				<DataTable ref="dt" :value="titulosPagarReceber" dataKey="id" :paginator="true" :rows="10"
							paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown" :rowsPerPageOptions="[5,10,25]"
							currentPageReportTemplate="Mostrando {first} até {last} de {totalRecords} lançamentos" responsiveLayout="scroll">
					<template #header>
						<div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
							<h5 class="m-0">Pagar e Receber</h5>
						</div>
					</template>

					<Column field="descricaoLancamento" header="Descrição do Lançamento" :sortable="true" headerStyle="width:30%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Descrição do Lançamento</span>
							{{slotProps.data.descricaoLancamento}}
						</template>
					</Column>

					<Column field="dataEmissao" header="Data de Emissão" :sortable="true" headerStyle="width:20%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Data de Emissão</span>
							{{formatDate(slotProps.data.dataEmissao)}}
						</template>
					</Column>

					<Column field="valorOperacao" header="Valor" :sortable="true" headerStyle="width:20%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Valor</span>
							{{slotProps.data.valorOperacao}}
						</template>
					</Column>

					<Column field="tipoOperacao" header="Tipo de Operação" :sortable="true" headerStyle="width:20%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Valor</span>
							{{slotProps.data.tipoOperacao}}
						</template>
					</Column>

					<Column field="situacao" header="Situação" :sortable="true" headerStyle="width:20%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Situação</span>
							{{slotProps.data.situacao}}
						</template>
					</Column>

					<Column headerStyle="min-width:10rem;">
						<template #body="slotProps">
							<Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editarPagarReceber(slotProps.data)" />
							<Button icon="pi pi-trash" class="p-button-rounded p-button-warning mt-2" @click="confirmarRemocaoPagarReceber(slotProps.data)" v-if="verificaPermissao(slotProps.data)"/>
						</template>
					</Column>
				</DataTable>

				<Dialog v-model:visible="pagarReceberDialog" :style="{width: '450px'}" header="Pagar e Receber" :modal="true" class="p-fluid">

					<div class="field">
						<label for="idFilialMovimento">Filial de Movimento</label>
						<Dropdown v-model="pagarReceber.idFilialMovimento" :options="filiais" optionLabel="nome" optionValue="id" placeholder="Escolha a Filial" />
						<small class="p-invalid" v-if="submitted && !pagarReceber.idFilialMovimento">Filial de Movimento é obrigatória.</small>
					</div>
					

					<div class="field">
						<label for="idFilialCobranca">Filial de Cobrança</label>
						<Dropdown v-model="pagarReceber.idFilialCobranca" :options="filiais" optionLabel="nome" optionValue="id" placeholder="Escolha a Filial" />
						<small class="p-invalid" v-if="submitted && !pagarReceber.idFilialCobranca">Filial de Cobrança é obrigatória.</small>
					</div>

					<div class="field">
						<label for="idPessoa">Pessoa</label>
						<Dropdown v-model="pagarReceber.idPessoa" :options="pessoas" optionLabel="nome" optionValue="id" placeholder="Escolha a Pessoa" />
						<small class="p-invalid" v-if="submitted && !pagarReceber.idPessoa">Pessoa é obrigatória.</small>
					</div>

					<div class="field">
						<label for="descricaoLancamento">Descrição do Lançamento</label>
						<InputText id="descricaoLancamento" v-model.trim="pagarReceber.descricaoLancamento" required="true" autofocus :class="{'p-invalid': submitted && !pagarReceber.descricaoLancamento}" />
						<small class="p-invalid" v-if="submitted && !pagarReceber.descricaoLancamento">Nome é obrigatório.</small>
					</div>

					<div class="field">
						<label class="mb-3">Tipo de Operação</label>
						<div class="formgrid grid">
							<div class="field-radiobutton col-6">
								<RadioButton id="PAGAR" name="tipoOperacao" value="PAGAR" v-model="pagarReceber.tipoOperacao" />
								<label for="PAGAR">Pagar</label>
							</div>
							<div class="field-radiobutton col-6">
								<RadioButton id="RECEBER" name="tipoOperacao" value="RECEBER" v-model="pagarReceber.tipoOperacao" />
								<label for="RECEBER">Receber</label>
							</div>
						</div>
					</div>

					<div class="field">
						<label for="dataEmissao">Data da Emissão</label>
						<Calendar v-model="pagarReceber.dataEmissao" dateFormat="dd/mm/yy" />
						<small class="p-invalid" v-if="submitted && !pagarReceber.dataEmissao">Data de Emissão é obrigatória.</small>
					</div>


					<div class="field">
						<label for="dataVencimento">Data da Vencimento</label>
						<Calendar v-model="pagarReceber.dataVencimento" dateFormat="dd/mm/yy" />
						<small class="p-invalid" v-if="submitted && !pagarReceber.dataVencimento">Data de Vencimento é obrigatória.</small>
					</div>

					<div class="field">
						<label for="valorOperacao">Valor da Operação</label>
						<InputNumber v-model="pagarReceber.valorOperacao" mode="currency" currency="BRL" locale="pt-BR" />
						<small class="p-invalid" v-if="submitted && !pagarReceber.valorOperacao">Valor é obrigatório.</small>
					</div>

					<div class="field">
						<label for="idContaFluxoCaixa">Conta Fluxo de Caixa</label>
						<Dropdown v-model="pagarReceber.idContaFluxoCaixa" :options="contasFluxoCaixa" optionLabel="nome" optionValue="id" placeholder="Escolha a Conta Fluxo de Caixa" />
						<small class="p-invalid" v-if="submitted && !pagarReceber.idContaFluxoCaixa">Conta Fluxo de Caixa é obrigatória.</small>
					</div>

					<template #footer>
						<Button label="Cancelar" icon="pi pi-times" class="p-button-text" @click="hideDialog"/>
						<Button label="Salvar" icon="pi pi-check" class="p-button-text" @click="salvarPagarReceber" v-if="verificaPermissao(pagarReceber)"/>
					</template>
				</Dialog>

				<Dialog v-model:visible="removerPagarReceberDialog" :style="{width: '450px'}" header="Confirm" :modal="true">
					<div class="flex align-items-center justify-content-center">
						<i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
						<span v-if="pagarReceber">Deseja remover remover o Lançamento <b>{{pagarReceber.nome}}</b>?</span>
					</div>
					<template #footer>
						<Button label="No" icon="pi pi-times" class="p-button-text" @click="removerPagarReceberDialog = false"/>
						<Button label="Yes" icon="pi pi-check" class="p-button-text" @click="removerPagarReceber" />
					</template>
				</Dialog>
			</div>
		</div>
	</div>

</template>

<script>
const API_RESOURCE = '/financeiro/pagarReceber';

export default {
	data() {
		return {
			titulosPagarReceber: null,
			pagarReceberDialog: false,
			removerPagarReceberDialog: false,
			pagarReceber: {},
			submitted: false,
			filiais: [],
			pessoas: [],
			contasFluxoCaixa: [],
		}
	},
	
	mounted() {
		
		this.atualizarTitulosPagarReceber();
		this.atualizarFiliais();
		this.atualizaPessoas();
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

		atualizarTitulosPagarReceber() {
			this.axios.get(API_RESOURCE)
				.then(resp => this.titulosPagarReceber = resp.data.content);
		},

		atualizarFiliais() {
			this.axios.get('/cadastro/filial')
				.then(resp => this.filiais = resp.data.content);
		},

		atualizaPessoas() {
			this.axios.get('/cadastro/pessoa')
				.then(resp => this.pessoas = resp.data.content);
		},

		atualizaContasFluxoCaixa() {
			this.axios.get('/financeiro/conta_fluxo_caixa')
				.then(resp => this.contasFluxoCaixa = resp.data.content);
		},

		openNew() {
			this.pagarReceber = {};
			this.submitted = false;
			this.pagarReceberDialog = true;
		},

		hideDialog() {
			this.pagarReceberDialog = false;
			this.submitted = false;
		},

		salvarPagarReceber() {
			this.submitted = true;
			
			const pagarReceber = {
				id: this.pagarReceber.id,
				idFilialMovimento: this.pagarReceber.idFilialMovimento,
				idFilialCobranca: this.pagarReceber.idFilialCobranca,
				idPessoa: this.pagarReceber.idPessoa,
				descricaoLancamento: this.pagarReceber.descricaoLancamento,
				idContaFluxoCaixa: this.pagarReceber.idContaFluxoCaixa,
				tipoOperacao: this.pagarReceber.tipoOperacao,
				dataEmissao: this.pagarReceber.dataEmissao,
				dataVencimento: this.pagarReceber.dataVencimento,
				valorOperacao: this.pagarReceber.valorOperacao,
				situacao: 'ABERTO'
			};

			console.log(pagarReceber);

			const header = {
				'Content-Type': 'application/json'
			}

			const onSucesso = res => {
				if(res.status == 200) {
					this.$toast.add({severity:'success', summary: 'Sucesso', detail: 'Pagar e Receber salvo com sucesso', life: 3000});
					this.pagarReceberDialog = false;
					this.pagarReceber = {};
					this.atualizarTitulosPagarReceber();
				} else {
					this.$toast.add({severity:'warning', summary: 'Erro', detail: 'Erro ao salvar Pagar e Receber', life: 3000});
				}
			}

			if(pagarReceber.id) {
				this.axios.put(API_RESOURCE+'/'+pagarReceber.id, pagarReceber, header)
					.then(onSucesso);
			} else {
				this.axios.post(API_RESOURCE, pagarReceber, header)
					.then(onSucesso);
			}
		},

		editarPagarReceber(pagarReceber) {
			this.axios.get(API_RESOURCE+'/'+pagarReceber.id)
				.then(resp => {
					this.pagarReceber = resp.data;
					this.pagarReceberDialog = true;
				});
			
		},

		confirmarRemocaoPagarReceber(pagarReceber) {
			this.pagarReceber = pagarReceber;
			this.removerPagarReceberDialog = true;
		},

		removerPagarReceber() {
			this.axios.delete(API_RESOURCE+'/'+this.pagarReceber.id)
				.then(() => {
					this.removerPagarReceberDialog = false;
					this.pagarReceber = {};
					this.$toast.add({severity:'success', summary: 'Sucesso', detail: 'Pagar e Receber removido com sucesso.', life: 3000});
					this.atualizarTitulosPagarReceber();
				})
				.catch((error) => {
					this.removerPagarReceberDialog = false;
					this.pagarReceber = {};
					this.$toast.add({severity:'error', summary: 'Error', detail: error.response.data.message, life: 3000});
				});
		},

		verificaPermissao(pagarReceber) {
			return pagarReceber.id == null || (pagarReceber.situacao == 'ABERTO' && pagarReceber.idOrigem == null);
		}
	}
}
</script>

<style scoped lang="scss">
@import '@/assets/demo/badges.scss';
</style>
