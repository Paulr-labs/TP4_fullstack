<template>
  <section class="cv-page container">
	<header class="cv-header">
	  <h1>CV</h1>
	  <p class="lead">Votre CV affiché directement ici.</p>
	</header>

	<div class="viewer">
	  <iframe src="/cvs/Cv_cyb_rp.pdf" frameborder="0" style="width:100%; height:80vh"></iframe>
	</div>
  </section>
</template>

<style scoped>
.cv-page { padding: 2rem 0 }
.cv-header h1 { margin: 0; font-size: 1.6rem }
.lead { color: var(--color-muted); margin-top: 0.25rem }
.viewer { margin-top: 1rem; background: white; border-radius: 8px; box-shadow: var(--shadow-sm); overflow: hidden }
</style>
