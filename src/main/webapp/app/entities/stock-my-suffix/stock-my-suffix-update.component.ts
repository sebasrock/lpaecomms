import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IStockMySuffix } from 'app/shared/model/stock-my-suffix.model';
import { StockMySuffixService } from './stock-my-suffix.service';

@Component({
    selector: 'jhi-stock-my-suffix-update',
    templateUrl: './stock-my-suffix-update.component.html'
})
export class StockMySuffixUpdateComponent implements OnInit {
    private _stock: IStockMySuffix;
    isSaving: boolean;

    constructor(private stockService: StockMySuffixService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stock }) => {
            this.stock = stock;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.stock.id !== undefined) {
            this.subscribeToSaveResponse(this.stockService.update(this.stock));
        } else {
            this.subscribeToSaveResponse(this.stockService.create(this.stock));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStockMySuffix>>) {
        result.subscribe((res: HttpResponse<IStockMySuffix>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get stock() {
        return this._stock;
    }

    set stock(stock: IStockMySuffix) {
        this._stock = stock;
    }
}
