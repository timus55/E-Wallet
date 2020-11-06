import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginByUsernameComponent } from './login-by-username.component';

describe('LoginByUsernameComponent', () => {
  let component: LoginByUsernameComponent;
  let fixture: ComponentFixture<LoginByUsernameComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginByUsernameComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginByUsernameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
