import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardUserComponent } from './components/dashboard-user/dashboard-user.component';
import { LoginRegisterComponent } from './components/login-registration/login-registration.component';
import { SellerDashboardComponent } from './components/seller-dashboard/seller-dashboard.component';
import { MyListAndHistoryComponent } from './components/my-list-and-history/my-list-and-history.component';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { ShowOneUserComponent } from './components/show-one-user/show-one-user.component';
import { ParticipateComponent } from './components/participate/participate.component';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { ProductByCategoryComponent } from './components/product-by-category/product-by-category.component';
import { ShowDetailsProductForBuyerComponent } from './components/show-details-product-for-buyer/show-details-product-for-buyer.component';
import { AddProductComponent } from './components/add-product/add-product.component';
import { LoginAdminComponent } from './components/login-admin/login-admin.component';
import { PurchaseConfirmationComponent } from './components/purchase-confirmation/purchase-confirmation.component';
import { ShowOneProductComponent } from './components/show-one-product/show-one-product.component';
import { EditprofilebuyerComponent } from './components/editprofilebuyer/editprofilebuyer.component';
import { EditprofilesellerComponent } from './components/editprofileseller/editprofileseller.component';
import { ProductByCategoryAuctionComponent } from './components/product-by-category-auction/product-by-category-auction.component';

const routes: Routes = [

  { path: '', component: LandingPageComponent },
  { path: 'dashboard', component: DashboardUserComponent },
  { path: 'register', component: LoginRegisterComponent },
  { path: 'sellerDashborad', component: SellerDashboardComponent },
  { path: 'product/add', component: AddProductComponent },
  { path: 'myList', component: MyListAndHistoryComponent },
  { path: 'user/edit', component: EditProfileComponent },
   { path: 'user/edit/buyer', component: EditprofilebuyerComponent },
  { path: 'user/edit/seller', component: EditprofilesellerComponent },

  { path: 'user/:id', component: ShowOneUserComponent },
  { path: 'product/:id/participate', component: ParticipateComponent },
  { path: 'product/:category/fixedPrices', component: ProductByCategoryComponent },
  { path: 'product/:category/auctions', component: ProductByCategoryAuctionComponent },
  { path: 'product/details/:id', component: ShowDetailsProductForBuyerComponent },
  { path: 'adminpage', component: LoginAdminComponent },
  { path: 'product/:id/selled', component: PurchaseConfirmationComponent },
  { path: 'product/:id/show', component: ShowOneProductComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
