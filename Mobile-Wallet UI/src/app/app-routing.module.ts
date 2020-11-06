import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { FooterComponent } from './components/footer/footer.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { HeaderComponent } from './components/header/header.component';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { HomeComponent } from './components/home/home.component';
import { LoginByAadharComponent } from './components/login-by-aadhar/login-by-aadhar.component';
import { GeneralHeaderComponent } from './components/general-header/general-header.component';
import { TransactionComponent } from './components/transaction/transaction.component';
import { LoginByUsernameComponent } from './components/login-by-username/login-by-username.component';
import { EditUserComponent } from './components/edit-user/edit-user.component';
import { TransferComponent } from './components/transfer/transfer.component';
import { AddCardComponent } from './components/add-card/add-card.component';
import { ViewCardsComponent } from './components/view-cards/view-cards.component';
import { AddMoneyComponent } from './components/add-money/add-money.component';
import { EnterAmountComponent } from './components/enter-amount/enter-amount.component';
import { AdminComponent } from './components/admin/admin.component';
import { AboutComponent } from './components/about/about.component';
import { ContactComponent } from './components/contact/contact.component';
import { NotfoundComponent } from './components/notfound/notfound.component';


const routes: Routes = [
  {path:'loginByNumber',component:LoginComponent},
  {path:'signup',component:SignupComponent},
  {path:'home',component:HomeComponent},
   {path:'about',component:AboutComponent},
  {path:'header',component:HeaderComponent},
  {path:'contact',component:ContactComponent},
  {path:'',component:LoginComponent},

  {path:'footer',component:FooterComponent},
  {path:'forgotPassword',component:ForgotPasswordComponent},
  {path:'changePassword', component:ChangePasswordComponent},
  {path:'generalheader',component:GeneralHeaderComponent},
  {path:'footer',component:FooterComponent},
  {path:'loginByUsername',component:LoginByUsernameComponent},
  {path:'loginByAadhar',component:LoginByAadharComponent},
  {path:'transaction',component:TransactionComponent},
  
  {path:'transaction',component:TransactionComponent},
  {path:'editUser',component:EditUserComponent},
  {path:'transfer',component:TransferComponent},
  {path:'notFound',component:NotfoundComponent},
  {path:'',redirectTo : 'loginByNumber',pathMatch:'full'},
  

  {path:'footer',component:FooterComponent},
  {path:'add-card',component:AddCardComponent},
  {path:'view-cards',component:ViewCardsComponent},
  {path:'add-money',component:AddMoneyComponent},
  {path:'enter-amount',component:EnterAmountComponent},
  {path:'footer',component:FooterComponent},
  {path:'admin',component:AdminComponent}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
