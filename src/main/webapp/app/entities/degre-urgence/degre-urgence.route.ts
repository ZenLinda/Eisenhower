import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDegreUrgence, DegreUrgence } from 'app/shared/model/degre-urgence.model';
import { DegreUrgenceService } from './degre-urgence.service';
import { DegreUrgenceComponent } from './degre-urgence.component';
import { DegreUrgenceDetailComponent } from './degre-urgence-detail.component';
import { DegreUrgenceUpdateComponent } from './degre-urgence-update.component';

@Injectable({ providedIn: 'root' })
export class DegreUrgenceResolve implements Resolve<IDegreUrgence> {
  constructor(private service: DegreUrgenceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDegreUrgence> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((degreUrgence: HttpResponse<DegreUrgence>) => {
          if (degreUrgence.body) {
            return of(degreUrgence.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DegreUrgence());
  }
}

export const degreUrgenceRoute: Routes = [
  {
    path: '',
    component: DegreUrgenceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'DegreUrgences'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DegreUrgenceDetailComponent,
    resolve: {
      degreUrgence: DegreUrgenceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DegreUrgences'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DegreUrgenceUpdateComponent,
    resolve: {
      degreUrgence: DegreUrgenceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DegreUrgences'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DegreUrgenceUpdateComponent,
    resolve: {
      degreUrgence: DegreUrgenceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'DegreUrgences'
    },
    canActivate: [UserRouteAccessService]
  }
];
