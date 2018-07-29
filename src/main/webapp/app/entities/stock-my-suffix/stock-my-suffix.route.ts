import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { StockMySuffix } from 'app/shared/model/stock-my-suffix.model';
import { StockMySuffixService } from './stock-my-suffix.service';
import { StockMySuffixComponent } from './stock-my-suffix.component';
import { StockMySuffixDetailComponent } from './stock-my-suffix-detail.component';
import { StockMySuffixUpdateComponent } from './stock-my-suffix-update.component';
import { StockMySuffixDeletePopupComponent } from './stock-my-suffix-delete-dialog.component';
import { IStockMySuffix } from 'app/shared/model/stock-my-suffix.model';

@Injectable({ providedIn: 'root' })
export class StockMySuffixResolve implements Resolve<IStockMySuffix> {
    constructor(private service: StockMySuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((stock: HttpResponse<StockMySuffix>) => stock.body));
        }
        return of(new StockMySuffix());
    }
}

export const stockRoute: Routes = [
    {
        path: 'stock-my-suffix',
        component: StockMySuffixComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'lpaecommsApp.stock.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stock-my-suffix/:id/view',
        component: StockMySuffixDetailComponent,
        resolve: {
            stock: StockMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lpaecommsApp.stock.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stock-my-suffix/new',
        component: StockMySuffixUpdateComponent,
        resolve: {
            stock: StockMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lpaecommsApp.stock.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stock-my-suffix/:id/edit',
        component: StockMySuffixUpdateComponent,
        resolve: {
            stock: StockMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lpaecommsApp.stock.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockPopupRoute: Routes = [
    {
        path: 'stock-my-suffix/:id/delete',
        component: StockMySuffixDeletePopupComponent,
        resolve: {
            stock: StockMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lpaecommsApp.stock.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
