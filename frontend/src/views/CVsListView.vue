<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getCVs } from '@/services/cv'

interface CVItem { id: number; name: string; url: string; uploadedAt?: string }

const cvs = ref<CVItem[]>([])
const loading = ref(false)
const error = ref('')
const router = useRouter()

async function loadCVs() {
	loading.value = true
	error.value = ''
	try {
		cvs.value = await getCVs()
	} catch (e) {
		error.value = 'Impossible de charger les CVs.'
	} finally {
		loading.value = false
	}
}

function openCV(id: number) {
	router.push({ name: 'cv-detail', params: { id } })
}

onMounted(loadCVs)
</script>

<template>
	<section class="cvs-page container">
		<header class="cvs-header">
			<h1>CVs</h1>
			<p class="lead">Consultez les CVs que l'administrateur a mis à disposition.</p>
		</header>

		<div v-if="loading" class="center">Chargement...</div>
		<div v-if="error" class="error">{{ error }}</div>

		<div v-if="!loading && cvs.length === 0" class="empty">Aucun CV disponible pour le moment.</div>

		<div class="grid" v-if="cvs.length">
			<article v-for="cv in cvs" :key="cv.id" class="card">
				<div class="card-body">
					<div class="card-title">{{ cv.name }}</div>
					<div class="card-sub">{{ cv.uploadedAt ? new Date(cv.uploadedAt).toLocaleDateString() : '' }}</div>
				</div>
				<div class="card-actions">
					<button class="btn btn-outline" @click="openCV(cv.id)">Voir</button>
					<a :href="cv.url" target="_blank" rel="noopener" class="btn btn-primary">Ouvrir</a>
				</div>
			</article>
		</div>
	</section>
</template>

<style scoped>
.cvs-page { padding: 2rem 0; }
.cvs-header { margin-bottom: 1.5rem; }
.cvs-header h1 { margin: 0; font-size: 1.75rem; }
.lead { color: var(--color-muted); margin-top: 0.25rem }
.grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(220px, 1fr)); gap: 1rem; }
.card { background: white; border-radius: 8px; padding: 1rem; box-shadow: var(--shadow-sm); display: flex; flex-direction: column; justify-content: space-between; }
.card-title { font-weight: 600; font-size: 1.05rem }
.card-sub { color: var(--color-muted); font-size: 0.9rem; margin-top: 0.25rem }
.card-actions { display:flex; gap:0.5rem; margin-top:1rem }
.center { text-align: center; padding: 2rem 0 }
.empty { color: var(--color-muted); padding: 1rem 0 }
.error { color: var(--color-danger); padding: 1rem 0 }
.btn { padding: 0.5rem 0.8rem; border-radius: 6px; }
.btn-outline { background: transparent; border: 1px solid var(--color-border); }
.btn-primary { background: var(--color-primary); color: white; border: none; }
</style>
