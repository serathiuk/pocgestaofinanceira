<template>
	<div class="grid">
		<div class="col-12">
			<div class="card">
				<Toast/>

				<DataTable ref="dt" :value="statusPagamento" dataKey="id" :paginator="true" :rows="10"
							paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown" :rowsPerPageOptions="[5,10,25]"
							currentPageReportTemplate="Mostrando {first} até {last} de {totalRecords} status" responsiveLayout="scroll">
					<template #header>
						<div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
							<h5 class="m-0">Status de Pagamento</h5>
						</div>
					</template>

					<Column field="nsu" header="NSU" :sortable="true" headerStyle="width:20%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">NSU</span>
							{{slotProps.data.nsu}}
						</template>
					</Column>
					
					<Column field="identificador" header="Identificador" :sortable="true" headerStyle="width:30%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Identificador</span>
							{{slotProps.data.identificador}}
						</template>
					</Column>

					<Column field="status" header="Status" :sortable="true" headerStyle="width:20%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Status</span>
							{{slotProps.data.status}}
						</template>
					</Column>


					<Column field="valorDocumento" header="Valor de Oocumento" :sortable="true" headerStyle="width:30%; min-width:10rem;">
						<template #body="slotProps">
							<span class="p-column-title">Valor de Oocumento</span>
							{{slotProps.data.valorDocumento}}
						</template>
					</Column>

				</DataTable>
			</div>
		</div>
	</div>

</template>

<script>
const API_RESOURCE = '/integracao/pagamento';

export default {
	data() {
		return {
			statusPagamento: []
		}
	},
	
	mounted() {
		this.atualizarStatusPagamento();
	},

	methods: {

		atualizarStatusPagamento() {
			this.axios.get(API_RESOURCE)
				.then(resp => this.statusPagamento = resp.data);
		}

	}
}
</script>

<style scoped lang="scss">
@import '@/assets/demo/badges.scss';
</style>
