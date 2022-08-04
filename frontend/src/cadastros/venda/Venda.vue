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

				<DataTable ref="dt" :value="vendas" dataKey="id" :paginator="true" :rows="10"
							paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown" :rowsPerPageOptions="[5,10,25]"
							currentPageReportTemplate="Mostrando {first} até {last} de {totalRecords} vendas" responsiveLayout="scroll">
					<template #header>
						<div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
							<h5 class="m-0">Vendas</h5>
						</div>
					</template>

					<Column field="nomeFilial" header="Filial" :sortable="true" headerStyle="width:30%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Filial</span>
							{{slotProps.data.nomeFilial}}
						</template>
					</Column>

					<Column field="nomePessoa" header="Pessoa" :sortable="true" headerStyle="width:30%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Pessoa</span>
							{{slotProps.data.nomePessoa}}
						</template>
					</Column>

					<Column field="valorVenda" header="Valor" :sortable="true" headerStyle="width:20%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Valor</span>
							{{slotProps.data.valorVenda}}
						</template>
					</Column>


					<Column field="statusVenda" header="Situação" :sortable="true" headerStyle="width:20%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Situação</span>
							{{slotProps.data.statusVenda}}
						</template>
					</Column>

					<Column headerStyle="min-width:10rem;">
						<template #body="slotProps">
							<Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editarVenda(slotProps.data)" />
							<Button icon="pi pi-trash" class="p-button-rounded p-button-warning mt-2" @click="confirmarRemocaoVenda(slotProps.data)" v-if="verificarEdicao(slotProps.data)"/>
						</template>
					</Column>
				</DataTable>

				<Dialog v-model:visible="vendaDialog" :style="{width: '450px'}" header="Venda" :modal="true" class="p-fluid">

					<div class="field">
						<label for="idFilial">Filial</label>
						<Dropdown v-model="venda.idFilial" :options="filiais" optionLabel="nome" optionValue="id" placeholder="Escolha a Filial" />
						<small class="p-invalid" v-if="submitted && !venda.idFilial">Filial de Movimento é obrigatória.</small>
					</div>

					<div class="field">
						<label for="idPessoa">Pessoa</label>
						<Dropdown v-model="venda.idPessoa" :options="pessoas" optionLabel="nome" optionValue="id" placeholder="Escolha a Pessoa" />
						<small class="p-invalid" v-if="submitted && !venda.idPessoa">Pessoa é obrigatória.</small>
					</div>

					<div class="field">
						<label for="valorOperacao">Valor da Venda</label>
						<InputNumber v-model="venda.valorVenda" mode="currency" currency="BRL" locale="pt-BR" />
						<small class="p-invalid" v-if="submitted && !venda.valorVenda">Valor é obrigatório.</small>
					</div>

					<template #footer>
						<Button label="Cancelar" icon="pi pi-times" class="p-button-text" @click="hideDialog"/>
						<Button label="Salvar" icon="pi pi-check" class="p-button-text" @click="salvarVenda" v-if="verificarEdicao(venda)"/>
					</template>
				</Dialog>

				<Dialog v-model:visible="removerVendaDialog" :style="{width: '450px'}" header="Confirm" :modal="true">
					<div class="flex align-items-center justify-content-center">
						<i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
						<span v-if="venda">Deseja remover remover o Lançamento <b>{{venda.nome}}</b>?</span>
					</div>
					<template #footer>
						<Button label="No" icon="pi pi-times" class="p-button-text" @click="removerVendaDialog = false"/>
						<Button label="Yes" icon="pi pi-check" class="p-button-text" @click="removerVenda" />
					</template>
				</Dialog>
			</div>
		</div>
	</div>

</template>

<script>
const API_RESOURCE = '/cadastro/venda';

export default {
	data() {
		return {
			vendas: null,
			vendaDialog: false,
			removerVendaDialog: false,
			venda: {},
			submitted: false,
			filiais: [],
			pessoas: [],
			contasFluxoCaixa: [],
		}
	},
	
	mounted() {
		
		this.atualizarVendas();
		this.atualizarFiliais();
		this.atualizaPessoas();
	},

	methods: {

        formatDate(value) {
            return new Date(value).toLocaleDateString('pt-BR', {
                day: '2-digit',
                month: '2-digit',
                year: 'numeric',
            });
        },

		atualizarVendas() {
			this.axios.get(API_RESOURCE)
				.then(resp => this.vendas = resp.data.content);
		},

		atualizarFiliais() {
			this.axios.get('/cadastro/filial')
				.then(resp => this.filiais = resp.data.content);
		},

		atualizaPessoas() {
			this.axios.get('/cadastro/pessoa')
				.then(resp => this.pessoas = resp.data.content);
		},

		openNew() {
			this.venda = {};
			this.submitted = false;
			this.vendaDialog = true;
		},

		hideDialog() {
			this.vendaDialog = false;
			this.submitted = false;
		},

		salvarVenda() {
			this.submitted = true;
			
			const venda = {
				id: this.venda.id,
				idFilial: this.venda.idFilial,
				idPessoa: this.venda.idPessoa,
				valorVenda: this.venda.valorVenda,
				statusVenda: 'PENDENTE'
			};

			const header = {
				'Content-Type': 'application/json'
			}

			const onSucesso = res => {
				if(res.status == 200) {
					this.$toast.add({severity:'success', summary: 'Sucesso', detail: 'Venda salvo com sucesso', life: 3000});
					this.vendaDialog = false;
					this.venda = {};
					this.atualizarVendas();
				} else {
					this.$toast.add({severity:'warning', summary: 'Erro', detail: 'Erro ao salvar Venda', life: 3000});
				}
			}

			if(venda.id) {
				this.axios.put(API_RESOURCE+'/'+venda.id, venda, header)
					.then(onSucesso);
			} else {
				this.axios.post(API_RESOURCE, venda, header)
					.then(onSucesso);
			}
		},

		editarVenda(venda) {
			this.axios.get(API_RESOURCE+'/'+venda.id)
				.then(resp => {
					this.venda = resp.data;
					this.vendaDialog = true;
				});
			
		},

		confirmarRemocaoVenda(venda) {
			this.venda = venda;
			this.removerVendaDialog = true;
		},

		removerVenda() {
			this.axios.delete(API_RESOURCE+'/'+this.venda.id)
				.then(() => {
					this.removerVendaDialog = false;
					this.venda = {};
					this.$toast.add({severity:'success', summary: 'Sucesso', detail: 'Venda removido com sucesso.', life: 3000});
					this.atualizarVendas();
				})
				.catch((error) => {
					this.removerVendaDialog = false;
					this.venda = {};
					this.$toast.add({severity:'error', summary: 'Error', detail: error.response.data.message, life: 3000});
				});
		},

		verificarEdicao(venda) {
			return venda.id == null || venda.statusVenda == 'PENDENTE';
		}
	}
}
</script>

<style scoped lang="scss">
@import '@/assets/demo/badges.scss';
</style>
