/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LpaecommsTestModule } from '../../../test.module';
import { StockMySuffixUpdateComponent } from 'app/entities/stock-my-suffix/stock-my-suffix-update.component';
import { StockMySuffixService } from 'app/entities/stock-my-suffix/stock-my-suffix.service';
import { StockMySuffix } from 'app/shared/model/stock-my-suffix.model';

describe('Component Tests', () => {
    describe('StockMySuffix Management Update Component', () => {
        let comp: StockMySuffixUpdateComponent;
        let fixture: ComponentFixture<StockMySuffixUpdateComponent>;
        let service: StockMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LpaecommsTestModule],
                declarations: [StockMySuffixUpdateComponent]
            })
                .overrideTemplate(StockMySuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StockMySuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockMySuffixService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StockMySuffix(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stock = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StockMySuffix();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stock = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
