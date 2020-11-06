import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserInfo } from 'src/app/models/user';
import { WalletService } from 'src/app/services/wallet.service';

import { Transaction } from '../../models/transaction';


@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent implements OnInit {

  transactions  : Transaction [] =[]
  users :UserInfo[]=[]
  transaction : Transaction = {
    id:0,
    statement:null,
    timeOfTransaction:null,
    transactionType:null,
    amount:0,
    cardNumber:0
  }

  
  constructor(private walletService : WalletService,private router:Router) { }

  
  ngOnInit() {
    if (localStorage.getItem("token") === null) 
      {
        alert("Session Expired..!! Please login or signup");
        this.router.navigate(['loginByNumber']);
      }
    this.transaction.statement = "";
    this.walletService.getTransactions(localStorage.token).subscribe(data=>{
     // this.transactions = data;
      this.transactions = data.sort((a, b) => {
        return <any>new Date(b.timeOfTransaction) - <any>new Date(a.timeOfTransaction);
      });

      console.log(this.transactions);
  
    },err=>{
      console.log(err)
    })

   
  }
  
  transactionStatus(u :Transaction){
    this.transaction = u;
    
  }
}
