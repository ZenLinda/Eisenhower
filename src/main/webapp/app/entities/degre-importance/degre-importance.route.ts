import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDegreImportance, DegreImportance } from 'app/shared/model/degre-importance.model';
import { DegreImportanceService } from './degre-importance.service';
import { DegreImportanceComponent } from './degre-importance.component';
import { DegreImportanceDetailComponent } from './degre-importance-detail.component';
import { DegreImportanceUpdateComponent } from './degre-importance-update.component';

@Injectable({ providedIn: 'root' })
export class DegreImportanceResolve implements Resolve<IDegreImportance> {
  constructor(private service: DegreImportanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDegreImportance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((degreImportance: HttpResponse<DegreImportance>) => {
          if (degreImportance.body) {
            return of(degreImportance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DegreImportance());
  }
}

export const degreImportanceRoute: Routes = [
  {
    path: '',
    component: DegreImportanceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'DegreImportances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DegreImportanceDetailComponent,
    resolve: {
      degreImportance: DegreImportanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DegreImportances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DegreImportanceUpdateComponent,
    resolve: {
      degreImportance: DegreImportanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DegreImportances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DegreImportanceUpdateComponent,
    resolve: {
      degreImportance: DegreImportanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DegreImportances'
    },
    canActivate: [UserRouteAccessService]
  }
];
