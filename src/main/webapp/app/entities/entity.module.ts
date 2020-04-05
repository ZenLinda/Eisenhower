import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'utilisateur',
        loadChildren: () => import('./utilisateur/utilisateur.module').then(m => m.EisenhowerUtilisateurModule)
      },
      {
        path: 'degre-urgence',
        loadChildren: () => import('./degre-urgence/degre-urgence.module').then(m => m.EisenhowerDegreUrgenceModule)
      },
      {
        path: 'degre-importance',
        loadChildren: () => import('./degre-importance/degre-importance.module').then(m => m.EisenhowerDegreImportanceModule)
      },
      {
        path: 'tache',
        loadChildren: () => import('./tache/tache.module').then(m => m.EisenhowerTacheModule)
      },
      {
        path: 'matrice',
        loadChildren: () => import('./matrice/matrice.module').then(m => m.EisenhowerMatriceModule)
      },
      {
        path: 'categorie-tache',
        loadChildren: () => import('./categorie-tache/categorie-tache.module').then(m => m.EisenhowerCategorieTacheModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class EisenhowerEntityModule {}
