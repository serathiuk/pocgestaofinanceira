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

				<DataTable ref="dt" :value="filiais" dataKey="id" :paginator="true" :rows="10"
							paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown" :rowsPerPageOptions="[5,10,25]"
							currentPageReportTemplate="Mostrando {first} até {last} de {totalRecords} filiais" responsiveLayout="scroll">
					<template #header>
						<div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
							<h5 class="m-0">Filiais</h5>
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
							<Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editarFilial(slotProps.data)" />
						</template>
					</Column>
				</DataTable>

				<Dialog v-model:visible="filialDialog" :style="{width: '450px'}" header="Filial" :modal="true" class="p-fluid">
					<div class="field">
						<label for="nome">Nome</label>
						<InputText id="nome" v-model.trim="filial.nome" required="true" autofocus :class="{'p-invalid': submitted && !filial.nome}" />
						<small class="p-invalid" v-if="submitted && !filial.nome">Nome é obrigatório.</small>
					</div>
					
					<template #footer>
						<Button label="Cancelar" icon="pi pi-times" class="p-button-text" @click="hideDialog"/>
						<Button label="Salvar" icon="pi pi-check" class="p-button-text" @click="salvarFilial" />
					</template>
				</Dialog>
			</div>
		</div>
	</div>

</template>

<script>
const API_RESOURCE = '/cadastro/filial';

export default {
	data() {
		return {
			filiais: null,
			filialDialog: false,
			removerFilialDialog: false,
			filial: {},
			submitted: false
		}
	},
	
	mounted() {
		this.atualizarFiliais();
	},

	methods: {

		atualizarFiliais() {
			this.axios.get(API_RESOURCE)
				.then(resp => this.filiais = resp.data.content);
		},

		openNew() {
			this.filial = {};
			this.submitted = false;
			this.filialDialog = true;
		},

		hideDialog() {
			this.filialDialog = false;
			this.submitted = false;
		},

		salvarFilial() {
			this.submitted = true;
			
			const filial = {
				id: this.filial.id,
				nome: this.filial.nome
			};

			const header = {
				'Content-Type': 'application/json'
			}

			const onSucesso = res => {
				if(res.status == 200) {
					this.$toast.add({severity:'success', summary: 'Sucesso', detail: 'Filial salva com sucesso', life: 3000});
					this.filialDialog = false;
					this.filial = {};
					this.atualizarFiliais();
				} else {
					this.$toast.add({severity:'warning', summary: 'Erro', detail: 'Erro ao salvar Filial', life: 3000});
				}
			}

			if(filial.id) {
				this.axios.put(API_RESOURCE+'/'+filial.id, filial, header)
					.then(onSucesso);
			} else {
				this.axios.post(API_RESOURCE, filial, header)
					.then(onSucesso);
			}
		},

		editarFilial(filial) {
			this.axios.get(API_RESOURCE+'/'+filial.id)
				.then(resp => {
					this.filial = resp.data;
					this.filialDialog = true;
				});
			
		}
	}
}
</script>

<style scoped lang="scss">
@import '@/assets/demo/badges.scss';
</style>
