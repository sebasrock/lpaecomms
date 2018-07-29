import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { InvoiceItemsMySuffix } from 'app/shared/model/invoice-items-my-suffix.model';
import { InvoiceItemsMySuffixService } from './invoice-items-my-suffix.service';
import { InvoiceItemsMySuffixComponent } from './invoice-items-my-suffix.component';
import { InvoiceItemsMySuffixDetailComponent } from './invoice-items-my-suffix-detail.component';
import { InvoiceItemsMySuffixUpdateComponent } from './invoice-items-my-suffix-update.component';
import { InvoiceItemsMySuffixDeletePopupComponent } from './invoice-items-my-suffix-delete-dialog.component';
import { IInvoiceItemsMySuffix } from 'app/shared/model/invoice-items-my-suffix.model';

@Injectable({ providedIn: 'root' })
export class InvoiceItemsMySuffixResolve implements Resolve<IInvoiceItemsMySuffix> {
    constructor(private service: InvoiceItemsMySuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((invoiceItems: HttpResponse<InvoiceItemsMySuffix>) => invoiceItems.body));
        }
        return of(new InvoiceItemsMySuffix());
    }
}

export const invoiceItemsRoute: Routes = [
    {
        path: 'invoice-items-my-suffix',
        component: InvoiceItemsMySuffixComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'lpaecommsApp.invoiceItems.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'invoice-items-my-suffix/:id/view',
        component: InvoiceItemsMySuffixDetailComponent,
        resolve: {
            invoiceItems: InvoiceItemsMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lpaecommsApp.invoiceItems.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'invoice-items-my-suffix/new',
        component: InvoiceItemsMySuffixUpdateComponent,
        resolve: {
            invoiceItems: InvoiceItemsMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lpaecommsApp.invoiceItems.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'invoice-items-my-suffix/:id/edit',
        component: InvoiceItemsMySuffixUpdateComponent,
        resolve: {
            invoiceItems: InvoiceItemsMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lpaecommsApp.invoiceItems.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const invoiceItemsPopupRoute: Routes = [
    {
        path: 'invoice-items-my-suffix/:id/delete',
        component: InvoiceItemsMySuffixDeletePopupComponent,
        resolve: {
            invoiceItems: InvoiceItemsMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lpaecommsApp.invoiceItems.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
