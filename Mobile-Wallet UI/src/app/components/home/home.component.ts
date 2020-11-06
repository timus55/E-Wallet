import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { WalletService } from 'src/app/services/wallet.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router:Router,private service:WalletService) { }

  ngOnInit() {
    if (localStorage.getItem("token") === null) 
    {
      alert("Session Expired..!! Please login or signup");
      this.router.navigate(['loginByNumber']);
    }
   }
getRole()
{
  this.service.getRole(localStorage.token).subscribe(data=>
    {
      localStorage.role=data;
      console.log(data);
    },
    err=>
    {
      console.log(err);
    })
    if(localStorage.role ==="user")
    {
      console.log("if");
      
      this.router.navigate(['notFound']);
    }
    else{
      this.router.navigate(['admin']);
    }
}


}

