import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICategorieTache, CategorieTache } from 'app/shared/model/categorie-tache.model';
import { CategorieTacheService } from './categorie-tache.service';
import { CategorieTacheComponent } from './categorie-tache.component';
import { CategorieTacheDetailComponent } from './categorie-tache-detail.component';
import { CategorieTacheUpdateComponent } from './categorie-tache-update.component';

@Injectable({ providedIn: 'root' })
export class CategorieTacheResolve implements Resolve<ICategorieTache> {
  constructor(private service: CategorieTacheService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategorieTache> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((categorieTache: HttpResponse<CategorieTache>) => {
          if (categorieTache.body) {
            return of(categorieTache.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CategorieTache());
  }
}

export const categorieTacheRoute: Routes = [
  {
    path: '',
    component: CategorieTacheComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'CategorieTaches'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CategorieTacheDetailComponent,
    resolve: {
      categorieTache: CategorieTacheResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CategorieTaches'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CategorieTacheUpdateComponent,
    resolve: {
      categorieTache: CategorieTacheResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CategorieTaches'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CategorieTacheUpdateComponent,
    resolve: {
      categorieTache: CategorieTacheResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'CategorieTaches'
    },
    canActivate: [UserRouteAccessService]
  }
];
