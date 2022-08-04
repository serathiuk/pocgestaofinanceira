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

				<DataTable ref="dt" :value="pessoas" dataKey="id" :paginator="true" :rows="10"
							paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown" :rowsPerPageOptions="[5,10,25]"
							currentPageReportTemplate="Mostrando {first} até {last} de {totalRecords} pessoas" responsiveLayout="scroll">
					<template #header>
						<div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
							<h5 class="m-0">Pessoas</h5>
						</div>
					</template>

					<Column field="nome" header="Nome" :sortable="true" headerStyle="width:50%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Bome</span>
							{{slotProps.data.nome}}
						</template>
					</Column>
					<Column field="tipoPessoa" header="Tipo" :sortable="true" headerStyle="width:20%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Tipo</span>
							{{slotProps.data.tipoPessoa}}
						</template>
					</Column>
					<Column field="documento" header="Documento" :sortable="true" headerStyle="width:30%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Documento</span>
							{{slotProps.data.documento}}
						</template>
					</Column>
					<Column headerStyle="min-width:10rem;">
						<template #body="slotProps">
							<Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editarPessoa(slotProps.data)" />
						</template>
					</Column>
				</DataTable>

				<Dialog v-model:visible="pessoaDialog" :style="{width: '450px'}" header="Pessoa" :modal="true" class="p-fluid">
					<div class="field">
						<label for="nome">Nome</label>
						<InputText id="nome" v-model.trim="pessoa.nome" required="true" autofocus :class="{'p-invalid': submitted && !pessoa.nome}" />
						<small class="p-invalid" v-if="submitted && !pessoa.nome">Nome é obrigatório.</small>
					</div>

					<div class="field">
						<label class="mb-3">Tipo</label>
						<div class="formgrid grid">
							<div class="field-radiobutton col-6">
								<RadioButton id="PESSOA_FISICA" name="tipoPessoa" value="PESSOA_FISICA" v-model="pessoa.tipoPessoa" />
								<label for="PESSOA_FISICA">Pessoa Física</label>
							</div>
							<div class="field-radiobutton col-6">
								<RadioButton id="PESSOA_JURIDICA" name="tipoPessoa" value="PESSOA_JURIDICA" v-model="pessoa.tipoPessoa" />
								<label for="PESSOA_JURIDICA">Pessoa Jurídica</label>
							</div>
						</div>
					</div>

					<div class="field">
						<label for="documento">Documento</label>
						<InputText id="documento" v-model.trim="pessoa.documento" required="true" autofocus :class="{'p-invalid': submitted && !pessoa.documento}" />
						<small class="p-invalid" v-if="submitted && !pessoa.name">Documento é obrigatório.</small>
					</div>

					<template #footer>
						<Button label="Cancelar" icon="pi pi-times" class="p-button-text" @click="hideDialog"/>
						<Button label="Salvar" icon="pi pi-check" class="p-button-text" @click="salvarPessoa" />
					</template>
				</Dialog>
			</div>
		</div>
	</div>

</template>

<script>
const API_RESOURCE = '/cadastro/pessoa';

export default {
	data() {
		return {
			pessoas: null,
			pessoaDialog: false,
			removerPessoaDialog: false,
			pessoa: {},
			submitted: false
		}
	},
	
	mounted() {
		this.atualizarPessoas();
	},

	methods: {

		atualizarPessoas() {
			this.axios.get(API_RESOURCE)
				.then(resp => this.pessoas = resp.data.content);
		},

		openNew() {
			this.pessoa = {};
			this.submitted = false;
			this.pessoaDialog = true;
		},

		hideDialog() {
			this.pessoaDialog = false;
			this.submitted = false;
		},

		salvarPessoa() {
			this.submitted = true;
			
			const pessoa = {
				id: this.pessoa.id,
				nome: this.pessoa.nome,
				tipoPessoa: this.pessoa.tipoPessoa,
				documento: this.pessoa.documento
			};

			const header = {
				'Content-Type': 'application/json'
			}

			const onSucesso = res => {
				if(res.status == 200) {
					this.$toast.add({severity:'success', summary: 'Sucesso', detail: 'Pessoa salva com sucesso', life: 3000});
					this.pessoaDialog = false;
					this.pessoa = {};
					this.atualizarPessoas();
				} else {
					this.$toast.add({severity:'warning', summary: 'Erro', detail: 'Erro ao salvar Pessoa', life: 3000});
				}
			}

			if(pessoa.id) {
				this.axios.put(API_RESOURCE+'/'+pessoa.id, pessoa, header)
					.then(onSucesso);
			} else {
				this.axios.post(API_RESOURCE, pessoa, header)
					.then(onSucesso);
			}
		},

		editarPessoa(pessoa) {
			this.axios.get(API_RESOURCE+'/'+pessoa.id)
				.then(resp => {
					this.pessoa = resp.data;
					this.pessoaDialog = true;
				});
			
		}
	}
}
</script>

<style scoped lang="scss">
@import '@/assets/demo/badges.scss';
</style>
