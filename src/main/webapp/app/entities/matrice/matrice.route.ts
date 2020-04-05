import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMatrice, Matrice } from 'app/shared/model/matrice.model';
import { MatriceService } from './matrice.service';
import { MatriceComponent } from './matrice.component';
import { MatriceDetailComponent } from './matrice-detail.component';
import { MatriceUpdateComponent } from './matrice-update.component';

@Injectable({ providedIn: 'root' })
export class MatriceResolve implements Resolve<IMatrice> {
  constructor(private service: MatriceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMatrice> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((matrice: HttpResponse<Matrice>) => {
          if (matrice.body) {
            return of(matrice.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Matrice());
  }
}

export const matriceRoute: Routes = [
  {
    path: '',
    component: MatriceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Matrices'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MatriceDetailComponent,
    resolve: {
      matrice: MatriceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Matrices'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MatriceUpdateComponent,
    resolve: {
      matrice: MatriceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Matrices'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MatriceUpdateComponent,
    resolve: {
      matrice: MatriceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Matrices'
    },
    canActivate: [UserRouteAccessService]
  }
];
