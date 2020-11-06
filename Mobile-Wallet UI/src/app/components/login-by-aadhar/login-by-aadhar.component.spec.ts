import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginByAadharComponent } from './login-by-aadhar.component';

describe('LoginByAadharComponent', () => {
  let component: LoginByAadharComponent;
  let fixture: ComponentFixture<LoginByAadharComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginByAadharComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginByAadharComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
