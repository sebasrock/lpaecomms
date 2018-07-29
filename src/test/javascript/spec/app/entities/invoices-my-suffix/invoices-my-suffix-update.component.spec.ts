/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LpaecommsTestModule } from '../../../test.module';
import { InvoicesMySuffixUpdateComponent } from 'app/entities/invoices-my-suffix/invoices-my-suffix-update.component';
import { InvoicesMySuffixService } from 'app/entities/invoices-my-suffix/invoices-my-suffix.service';
import { InvoicesMySuffix } from 'app/shared/model/invoices-my-suffix.model';

describe('Component Tests', () => {
    describe('InvoicesMySuffix Management Update Component', () => {
        let comp: InvoicesMySuffixUpdateComponent;
        let fixture: ComponentFixture<InvoicesMySuffixUpdateComponent>;
        let service: InvoicesMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LpaecommsTestModule],
                declarations: [InvoicesMySuffixUpdateComponent]
            })
                .overrideTemplate(InvoicesMySuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InvoicesMySuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InvoicesMySuffixService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new InvoicesMySuffix(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.invoices = entity;
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
                    const entity = new InvoicesMySuffix();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.invoices = entity;
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
