import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDegreUrgence } from 'app/shared/model/degre-urgence.model';

type EntityResponseType = HttpResponse<IDegreUrgence>;
type EntityArrayResponseType = HttpResponse<IDegreUrgence[]>;

@Injectable({ providedIn: 'root' })
export class DegreUrgenceService {
  public resourceUrl = SERVER_API_URL + 'api/degre-urgences';

  constructor(protected http: HttpClient) {}

  create(degreUrgence: IDegreUrgence): Observable<EntityResponseType> {
    return this.http.post<IDegreUrgence>(this.resourceUrl, degreUrgence, { observe: 'response' });
  }

  update(degreUrgence: IDegreUrgence): Observable<EntityResponseType> {
    return this.http.put<IDegreUrgence>(this.resourceUrl, degreUrgence, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDegreUrgence>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDegreUrgence[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
