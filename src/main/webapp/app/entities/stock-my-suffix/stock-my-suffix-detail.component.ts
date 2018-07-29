import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockMySuffix } from 'app/shared/model/stock-my-suffix.model';

@Component({
    selector: 'jhi-stock-my-suffix-detail',
    templateUrl: './stock-my-suffix-detail.component.html'
})
export class StockMySuffixDetailComponent implements OnInit {
    stock: IStockMySuffix;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stock }) => {
            this.stock = stock;
        });
    }

    previousState() {
        window.history.back();
    }
}
