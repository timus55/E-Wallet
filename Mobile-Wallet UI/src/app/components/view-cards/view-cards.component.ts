import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { card } from 'src/app/models/card';
import { WalletService } from 'src/app/services/Wallet.service';

@Component({
  selector: 'app-view-cards',
  templateUrl: './view-cards.component.html',
  styleUrls: ['./view-cards.component.css']
})
export class ViewCardsComponent implements OnInit {
  cards: card[];
  emptyy: number = 1;
  temp: boolean;
  message:any;
  constructor(private walletService: WalletService, private router: Router) { }

  ngOnInit() {
    if (localStorage.getItem("token") === null) 
      {
        alert("Session Expired..!! Please login or signup");
        this.router.navigate(['loginByNumber']);
      }
    this.getCards();
    this.checkBalance();
  }
  checkBalance()
  {
    let b=this.walletService.check(localStorage.token);
    b.subscribe(data=>
      {
        this.message=data
      },
      err=>
      {
        console.log(err);
      }
      );
  }

  getCards() {
    this.walletService.viewCards(localStorage.token).subscribe(data => {
      this.cards = data;
    },
      err => {
        console.log(err.stack);
      })
  }
  addCard()
  {
    this.router.navigate(['/add-card']);
  }
  delete(cardNumber) {
    console.log(cardNumber);
    this.walletService.deleteCard(cardNumber,localStorage.token).subscribe(data => {
      console.log(data);
      alert(`deleted`);
      this.walletService.viewCards(localStorage.token).subscribe(data => { //localstorage should have username
        this.cards = data;
        console.log(this.cards);
      })
    })
  }

    deleteAll() {
    
      this.walletService.deleteAllCards(localStorage.token).subscribe(data => {
        console.log(data);
        alert(`deleted All Cards`);
        this.walletService.viewCards(localStorage.token).subscribe(data => { //localstorage should have username
          this.cards = data;
          console.log(this.cards);
        })
      })
    }

    addMoney(cardNumber,cvv) {
      localStorage.card=cardNumber;
      localStorage.cvv=cvv;
      console.log(cardNumber);
      this.router.navigate(['/enter-amount']);
        
      
    }
  

}
