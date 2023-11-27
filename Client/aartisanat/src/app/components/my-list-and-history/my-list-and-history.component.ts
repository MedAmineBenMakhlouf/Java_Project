import { ProductsService } from 'src/app/services/product.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-my-list-and-history',
  templateUrl: './my-list-and-history.component.html',
  styleUrls: ['./my-list-and-history.component.scss']
})
export class MyListAndHistoryComponent {
  listData: any;
  constructor(private productsService: ProductsService, private router: Router) { }

  ngOnInit() {
    this.productsService.getMyList().subscribe({
      next: (data) => {
        this.listData = data;
        console.log(data);
        
        if (this.listData.loggedUser.id != sessionStorage.getItem("user_id")) {
          this.router.navigateByUrl('dashboard');
        }

      },
      error: (error) => {
        console.error('There was an error!', error);
      }
    });
  }


  showProduct(id:any)
  {
    this.router.navigateByUrl(`product/${id}`);
  }
}
