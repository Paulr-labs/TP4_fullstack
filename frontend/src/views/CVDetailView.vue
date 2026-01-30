<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

type CVEntry = {
  id: string
  name: string
  filename: string
  owner?: string
  description?: string
}

const route = useRoute()
const cv = ref<CVEntry | null>(null)
const loading = ref(true)
const error = ref<string | null>(null)

async function loadDetail() {
  loading.value = true
  error.value = null
  try {
    const id = String(route.params.id)
    const res = await fetch(`/api/cvs/${id}`)
    if (!res.ok) throw new Error('Impossible de charger')
    cv.value = await res.json()
    if (!cv.value) error.value = 'CV introuvable'
  } catch (e: any) {
    error.value = e.message || 'Erreur'
  } finally {
    loading.value = false
  }
}

onMounted(loadDetail)
</script>

<template>
  <section class="container">
    <div v-if="loading">Chargement…</div>
    <div v-if="error" class="error">{{ error }}</div>

    <div v-if="cv" class="cv-detail">
      <h1>{{ cv.name }}</h1>
      <p class="muted">Par {{ cv.owner || 'Anonyme' }}</p>
      <p v-if="cv.description">{{ cv.description }}</p>

      <div class="pdf-wrapper" v-if="cv.filename">
        <iframe :src="`/api/cvs/files/${cv.filename}`" style="width:100%;height:80vh;border:0"></iframe>
      </div>

      <div class="actions" style="margin-top:1rem">
        <a :href="`/cvs/${cv.filename}`" target="_blank" rel="noopener" class="btn btn-primary">Télécharger / Ouvrir</a>
      </div>
    </div>
  </section>
</template>

<style scoped>
.muted { color: var(--color-muted, #666) }
.pdf-wrapper { border: 1px solid var(--color-border); border-radius: 6px; overflow: hidden }
</style>
