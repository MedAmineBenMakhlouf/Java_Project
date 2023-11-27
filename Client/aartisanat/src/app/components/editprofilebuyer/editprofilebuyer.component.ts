import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ProductsService } from 'src/app/services/product.service';
import { UsersService } from 'src/app/services/user.service';

@Component({
  selector: 'app-editprofilebuyer',
  templateUrl: './editprofilebuyer.component.html',
  styleUrls: ['./editprofilebuyer.component.scss']
})
export class EditprofilebuyerComponent {
  product :any;
  constructor(
    private userService: UsersService,
    private productService: ProductsService,
    private router: Router
  ) {}

  ngOnInit() {
    
    this.productService.getboughtProducts().subscribe({
      next: (data) => {
        this.product = data
        console.log(this.product);
        
      },
      error: (error) => console.error('There was an error!', error)
    });
  }

  


  dashboardUser() {
    this.router.navigateByUrl('/dashboard');
  }
  dashboardSeller() {
    this.router.navigateByUrl('/sellerDashborad');
  }
  logout() {
    this.userService.logoutUser();
    sessionStorage.removeItem("user_id");
    this.router.navigateByUrl('/register');
  }


  
  goToEditProfile()
  {
    this.router.navigateByUrl("/user/edit");
  }
  goToSellerHistory()
  {
    this.router.navigateByUrl("/user/edit/seller");
  }
  goToBuyerHistory()
  {
    this.router.navigateByUrl("/user/edit/buyer");
  }
}
