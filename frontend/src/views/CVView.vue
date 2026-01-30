<script setup lang="ts">
import { computed } from 'vue'
import { useAuthStore } from '@/stores/auth'

const authStore = useAuthStore()
const user = computed(() => authStore.user || { username: 'Utilisateur', email: '' })
</script>

<template>
  <section class="cv-view container">
    <h1>Mon CV</h1>

    <div class="cv-header">
      <div class="avatar">👤</div>
      <div class="meta">
        <h2>{{ user.username }}</h2>
        <p class="muted">{{ user.email }}</p>
      </div>
    </div>

    <article class="cv-content">
      <section>
        <h3>À propos</h3>
        <p>
          Ajoutez ici une courte présentation. Cette vue est un emplacement pour afficher
          votre CV. Vous pouvez remplacer ce contenu par les données de profil réelles.
        </p>
      </section>

      <section>
        <h3>Expériences</h3>
        <ul>
          <li>Expérience 1 — Description courte</li>
          <li>Expérience 2 — Description courte</li>
        </ul>
      </section>

      <section>
        <h3>Compétences</h3>
        <ul class="skills">
          <li>Java</li>
          <li>Spring Boot</li>
          <li>Vue.js / TypeScript</li>
        </ul>
      </section>

      <div class="cv-actions">
        <button class="btn btn-primary" @click="$router.back()">Retour</button>
      </div>
    </article>
  </section>
</template>

<style scoped>
.cv-view {
  padding: 2rem 1rem;
}
.cv-header {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 1.5rem;
}
.avatar {
  font-size: 3rem;
}
.meta h2 {
  margin: 0;
}
.muted {
  color: var(--color-muted, #666);
  font-size: 0.95rem;
}
.cv-content section {
  margin-bottom: 1rem;
}
.skills {
  display: flex;
  gap: 0.75rem;
  flex-wrap: wrap;
  list-style: none;
  padding: 0;
}
.skills li {
  background: var(--color-muted, #eee);
  padding: 0.25rem 0.5rem;
  border-radius: 6px;
}
.cv-actions {
  margin-top: 1.5rem;
}
</style>
