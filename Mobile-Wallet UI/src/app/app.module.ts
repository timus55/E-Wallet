import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { MaterialModule } from './material/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AdminComponent } from './components/admin/admin.component';
import { HttpClientModule } from '@angular/common/http';

import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';

import { TransactionComponent } from './components/transaction/transaction.component';
import { LoginByAadharComponent } from './components/login-by-aadhar/login-by-aadhar.component';
import { LoginByUsernameComponent } from './components/login-by-username/login-by-username.component';
import { GeneralHeaderComponent } from './components/general-header/general-header.component';
import { HomeComponent } from './components/home/home.component';


import { EditUserComponent } from './components/edit-user/edit-user.component';
import { TransferComponent } from './components/transfer/transfer.component';



import { AddCardComponent } from './components/add-card/add-card.component';
import { ViewCardsComponent } from './components/view-cards/view-cards.component';

import { AddMoneyComponent } from './components/add-money/add-money.component';
import { EnterAmountComponent } from './components/enter-amount/enter-amount.component';
import { AboutComponent } from './components/about/about.component';
import { ContactComponent } from './components/contact/contact.component';
import { NotfoundComponent } from './components/notfound/notfound.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    SignupComponent,
    LoginComponent,
    ForgotPasswordComponent,
    ChangePasswordComponent,
    TransactionComponent,
    LoginByAadharComponent,
    LoginByUsernameComponent,
    GeneralHeaderComponent,
    HomeComponent,
    EditUserComponent,
    TransferComponent,

    TransactionComponent,
    AddCardComponent,
    ViewCardsComponent,
    AddMoneyComponent,
    EnterAmountComponent,
    AdminComponent,
    AboutComponent,
    ContactComponent,
    NotfoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
