/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LpaecommsTestModule } from '../../../test.module';
import { StockMySuffixDetailComponent } from 'app/entities/stock-my-suffix/stock-my-suffix-detail.component';
import { StockMySuffix } from 'app/shared/model/stock-my-suffix.model';

describe('Component Tests', () => {
    describe('StockMySuffix Management Detail Component', () => {
        let comp: StockMySuffixDetailComponent;
        let fixture: ComponentFixture<StockMySuffixDetailComponent>;
        const route = ({ data: of({ stock: new StockMySuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LpaecommsTestModule],
                declarations: [StockMySuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StockMySuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StockMySuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.stock).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
