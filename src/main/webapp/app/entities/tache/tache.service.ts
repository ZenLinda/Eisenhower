import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITache } from 'app/shared/model/tache.model';

type EntityResponseType = HttpResponse<ITache>;
type EntityArrayResponseType = HttpResponse<ITache[]>;

@Injectable({ providedIn: 'root' })
export class TacheService {
  public resourceUrl = SERVER_API_URL + 'api/taches';

  constructor(protected http: HttpClient) {}

  create(tache: ITache): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tache);
    return this.http
      .post<ITache>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tache: ITache): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tache);
    return this.http
      .put<ITache>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITache>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITache[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tache: ITache): ITache {
    const copy: ITache = Object.assign({}, tache, {
      dateIdeale: tache.dateIdeale && tache.dateIdeale.isValid() ? tache.dateIdeale.format(DATE_FORMAT) : undefined,
      dateAlerte: tache.dateAlerte && tache.dateAlerte.isValid() ? tache.dateAlerte.format(DATE_FORMAT) : undefined,
      dateDelai: tache.dateDelai && tache.dateDelai.isValid() ? tache.dateDelai.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateIdeale = res.body.dateIdeale ? moment(res.body.dateIdeale) : undefined;
      res.body.dateAlerte = res.body.dateAlerte ? moment(res.body.dateAlerte) : undefined;
      res.body.dateDelai = res.body.dateDelai ? moment(res.body.dateDelai) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((tache: ITache) => {
        tache.dateIdeale = tache.dateIdeale ? moment(tache.dateIdeale) : undefined;
        tache.dateAlerte = tache.dateAlerte ? moment(tache.dateAlerte) : undefined;
        tache.dateDelai = tache.dateDelai ? moment(tache.dateDelai) : undefined;
      });
    }
    return res;
  }
}
