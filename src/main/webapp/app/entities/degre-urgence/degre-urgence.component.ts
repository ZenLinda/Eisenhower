import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDegreUrgence } from 'app/shared/model/degre-urgence.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DegreUrgenceService } from './degre-urgence.service';
import { DegreUrgenceDeleteDialogComponent } from './degre-urgence-delete-dialog.component';

@Component({
  selector: 'jhi-degre-urgence',
  templateUrl: './degre-urgence.component.html'
})
export class DegreUrgenceComponent implements OnInit, OnDestroy {
  degreUrgences?: IDegreUrgence[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected degreUrgenceService: DegreUrgenceService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.degreUrgenceService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IDegreUrgence[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInDegreUrgences();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDegreUrgence): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDegreUrgences(): void {
    this.eventSubscriber = this.eventManager.subscribe('degreUrgenceListModification', () => this.loadPage());
  }

  delete(degreUrgence: IDegreUrgence): void {
    const modalRef = this.modalService.open(DegreUrgenceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.degreUrgence = degreUrgence;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IDegreUrgence[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/degre-urgence'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.degreUrgences = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
