import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ProductsService } from 'src/app/services/product.service';
import { UsersService } from 'src/app/services/user.service';

@Component({
  selector: 'app-editprofileseller',
  templateUrl: './editprofileseller.component.html',
  styleUrls: ['./editprofileseller.component.scss']
})
export class EditprofilesellerComponent {
  product: any = {};
  constructor(
    private userService: UsersService,
    private productService: ProductsService,
    private router: Router
  ) { }

  ngOnInit() {
    this.productService.getselledProducts().subscribe({
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
  goToBuyerHistory() {
    this.router.navigateByUrl("/user/edit/buyer");
  }
  goToEditProfile() {
    this.router.navigateByUrl("/user/edit");
  }
  goToSellerHistory() {
    this.router.navigateByUrl("/user/edit/seller");
  }
}
