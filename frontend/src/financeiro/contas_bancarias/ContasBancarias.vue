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

				<DataTable ref="dt" :value="contasBancarias" dataKey="id" :paginator="true" :rows="10"
							paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown" :rowsPerPageOptions="[5,10,25]"
							currentPageReportTemplate="Mostrando {first} até {last} de {totalRecords} contas bancárias" responsiveLayout="scroll">
					<template #header>
						<div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
							<h5 class="m-0">Contas Bancarias</h5>
						</div>
					</template>

					<Column field="nome" header="Nome" :sortable="true" headerStyle="width:70%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Bome</span>
							{{slotProps.data.nome}}
						</template>
					</Column>
					<Column field="tipoConta" header="Tipo" :sortable="true" headerStyle="width:30%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Tipo</span>
							{{slotProps.data.tipoConta}}
						</template>
					</Column>
					<Column headerStyle="min-width:10rem;">
						<template #body="slotProps">
							<Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editarContaBancaria(slotProps.data)" />
						</template>
					</Column>
				</DataTable>

				<Dialog v-model:visible="contaBancariaDialog" :style="{width: '450px'}" header="ContaBancaria" :modal="true" class="p-fluid">
					<div class="field">
						<label for="nome">Nome</label>
						<InputText id="nome" v-model.trim="contaBancaria.nome" required="true" autofocus :class="{'p-invalid': submitted && !contaBancaria.nome}" />
						<small class="p-invalid" v-if="submitted && !contaBancaria.nome">Nome é obrigatório.</small>
					</div>

					<div class="field">
						<label class="mb-3">Tipo</label>
						<div class="formgrid grid">
							<div class="field-radiobutton col-3">
								<RadioButton id="CAIXA" name="tipoConta" value="CAIXA" v-model="contaBancaria.tipoConta" />
								<label for="CAIXA">Caixa</label>
							</div>
							<div class="field-radiobutton col-4">
								<RadioButton id="CONTA_CORRENTE" name="tipoConta" value="CONTA_CORRENTE" v-model="contaBancaria.tipoConta" />
								<label for="CONTA_CORRENTE">Conta Corrente</label>
							</div>
							<div class="field-radiobutton col-5">
								<RadioButton id="CONTA_POUPANCA" name="tipoConta" value="CONTA_POUPANCA" v-model="contaBancaria.tipoConta" />
								<label for="CONTA_POUPANCA">Conta Poupança</label>
							</div>
						</div>
					</div>

					<template #footer>
						<Button label="Cancelar" icon="pi pi-times" class="p-button-text" @click="hideDialog"/>
						<Button label="Salvar" icon="pi pi-check" class="p-button-text" @click="salvarContaBancaria" />
					</template>
				</Dialog>
			</div>
		</div>
	</div>

</template>

<script>
const API_RESOURCE = '/financeiro/conta_bancaria';

export default {
	data() {
		return {
			contasBancarias: null,
			contaBancariaDialog: false,
			contaBancaria: {},
			submitted: false
		}
	},
	
	mounted() {
		this.atualizarContasBancarias();
	},

	methods: {

		atualizarContasBancarias() {
			this.axios.get(API_RESOURCE)
				.then(resp => this.contasBancarias = resp.data.content);
		},

		openNew() {
			this.contaBancaria = {};
			this.submitted = false;
			this.contaBancariaDialog = true;
		},

		hideDialog() {
			this.contaBancariaDialog = false;
			this.submitted = false;
		},

		salvarContaBancaria() {
			this.submitted = true;
			
			const contaBancaria = {
				id: this.contaBancaria.id,
				nome: this.contaBancaria.nome,
				tipoConta: this.contaBancaria.tipoConta
			};

			const header = {
				'Content-Type': 'application/json'
			}

			const onSucesso = res => {
				if(res.status == 200) {
					this.$toast.add({severity:'success', summary: 'Sucesso', detail: 'Conta Bancária salva com sucesso', life: 3000});
					this.contaBancariaDialog = false;
					this.contaBancaria = {};
					this.atualizarContasBancarias();
				} else {
					this.$toast.add({severity:'warning', summary: 'Erro', detail: 'Erro ao salvar Conta Bancária', life: 3000});
				}
			}

			if(contaBancaria.id) {
				this.axios.put(API_RESOURCE+'/'+contaBancaria.id, contaBancaria, header)
					.then(onSucesso);
			} else {
				this.axios.post(API_RESOURCE, contaBancaria, header)
					.then(onSucesso);
			}
		},

		editarContaBancaria(contaBancaria) {
			this.axios.get(API_RESOURCE+'/'+contaBancaria.id)
				.then(resp => {
					this.contaBancaria = resp.data;
					this.contaBancariaDialog = true;
				});
			
		}
	}
}
</script>

<style scoped lang="scss">
@import '@/assets/demo/badges.scss';
</style>
