<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

type CVEntry = {
  id: string
  name: string
  filename: string
  owner?: string
  description?: string
}

const cvs = ref<CVEntry[]>([])
const loading = ref(true)
const error = ref<string | null>(null)
const router = useRouter()

async function loadList() {
  loading.value = true
  error.value = null
  try {
    const res = await fetch('/api/cvs')
    if (!res.ok) throw new Error('Impossible de charger la liste')
    cvs.value = await res.json()
  } catch (e: any) {
    error.value = e.message || 'Erreur'
  } finally {
    loading.value = false
  }
}

function openDetail(cv: CVEntry) {
  router.push({ name: 'cv-detail', params: { id: cv.id } })
}

onMounted(loadList)
</script>

<template>
  <section class="container">
    <h1>CVs publics</h1>

    <div v-if="loading">Chargement…</div>
    <div v-if="error" class="error">{{ error }}</div>

    <ul v-if="!loading && !error" class="cv-list">
      <li v-for="cv in cvs" :key="cv.id" class="cv-item">
        <div class="cv-main">
          <h3>{{ cv.name }}</h3>
          <p class="muted">Par {{ cv.owner || 'Anonyme' }}</p>
          <p v-if="cv.description">{{ cv.description }}</p>
        </div>
        <div class="cv-actions">
          <button class="btn btn-secondary" @click="openDetail(cv)">Voir</button>
          <a :href="`/api/cvs/files/${cv.filename}`" target="_blank" rel="noopener" class="btn btn-primary">Ouvrir (nouvel onglet)</a>
        </div>
      </li>
    </ul>
  </section>
</template>

<style scoped>
.cv-list { list-style: none; padding: 0; margin: 1rem 0; display: grid; gap: 1rem }
.cv-item { display: flex; justify-content: space-between; align-items: center; padding: 1rem; border: 1px solid var(--color-border); border-radius: 8px }
.cv-main h3 { margin: 0 0 0.25rem 0 }
.cv-actions { display: flex; gap: 0.5rem }
.muted { color: var(--color-muted, #666); font-size: 0.95rem }
</style>
