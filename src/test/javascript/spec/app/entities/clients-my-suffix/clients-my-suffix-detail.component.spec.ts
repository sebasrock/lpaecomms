/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LpaecommsTestModule } from '../../../test.module';
import { ClientsMySuffixDetailComponent } from 'app/entities/clients-my-suffix/clients-my-suffix-detail.component';
import { ClientsMySuffix } from 'app/shared/model/clients-my-suffix.model';

describe('Component Tests', () => {
    describe('ClientsMySuffix Management Detail Component', () => {
        let comp: ClientsMySuffixDetailComponent;
        let fixture: ComponentFixture<ClientsMySuffixDetailComponent>;
        const route = ({ data: of({ clients: new ClientsMySuffix(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LpaecommsTestModule],
                declarations: [ClientsMySuffixDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ClientsMySuffixDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClientsMySuffixDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.clients).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
