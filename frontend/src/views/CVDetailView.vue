<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getCV } from '@/services/cv'

const route = useRoute()
const router = useRouter()
const id = Number(route.params.id)

const cv = ref<{ id: number; name: string; url: string; uploadedAt?: string } | null>(null)
const loading = ref(false)
const error = ref('')

onMounted(async () => {
	if (!id) {
		error.value = 'CV introuvable.'
		return
	}
	loading.value = true
	try {
		cv.value = await getCV(id)
	} catch (e) {
		error.value = 'Impossible de charger le CV.'
	} finally {
		loading.value = false
	}
})

function goBack() {
	router.back()
}
</script>

<template>
	<section class="cv-detail container">
		<button class="btn btn-outline" @click="goBack">Retour</button>

		<div v-if="loading" class="center">Chargement...</div>
		<div v-if="error" class="error">{{ error }}</div>

		<div v-if="cv" class="viewer">
			<header class="viewer-header">
				<h2>{{ cv.name }}</h2>
				<p class="meta">{{ cv.uploadedAt ? new Date(cv.uploadedAt).toLocaleString() : '' }}</p>
			</header>
			<div class="embed">
				<iframe :src="cv.url" frameborder="0" style="width:100%; height:80vh"></iframe>
			</div>
		</div>
	</section>
</template>

<style scoped>
.cv-detail { padding: 1.5rem 0 }
.center { text-align:center; padding:2rem 0 }
.error { color: var(--color-danger); padding: 1rem 0 }
.viewer-header { margin: 1rem 0 }
.viewer-header h2 { margin: 0 }
.meta { color: var(--color-muted); margin-top: 0.25rem }
.embed { margin-top: 1rem; background: white; border-radius: 8px; box-shadow: var(--shadow-sm); overflow: hidden }
.btn-outline { padding: 0.4rem 0.8rem; border: 1px solid var(--color-border); background: transparent; border-radius: 6px }
</style>
