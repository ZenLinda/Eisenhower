import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDegreImportance } from 'app/shared/model/degre-importance.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DegreImportanceService } from './degre-importance.service';
import { DegreImportanceDeleteDialogComponent } from './degre-importance-delete-dialog.component';

@Component({
  selector: 'jhi-degre-importance',
  templateUrl: './degre-importance.component.html'
})
export class DegreImportanceComponent implements OnInit, OnDestroy {
  degreImportances?: IDegreImportance[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected degreImportanceService: DegreImportanceService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.degreImportanceService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IDegreImportance[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInDegreImportances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDegreImportance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDegreImportances(): void {
    this.eventSubscriber = this.eventManager.subscribe('degreImportanceListModification', () => this.loadPage());
  }

  delete(degreImportance: IDegreImportance): void {
    const modalRef = this.modalService.open(DegreImportanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.degreImportance = degreImportance;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IDegreImportance[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/degre-importance'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.degreImportances = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
