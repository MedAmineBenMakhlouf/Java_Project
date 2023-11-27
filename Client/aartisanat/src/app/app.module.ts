import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {  LoginRegisterComponent } from './components/login-registration/login-registration.component';

import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { HttpClientModule } from '@angular/common/http';

import { FormsModule } from '@angular/forms';
import { DashboardUserComponent } from './components/dashboard-user/dashboard-user.component';
import { SellerDashboardComponent } from './components/seller-dashboard/seller-dashboard.component';
import { NavbarSellerComponent } from './components/navbar-seller/navbar-seller.component';
import { EditProfileComponent } from './components/edit-profile/edit-profile.component';
import { AddProductComponent } from './components/add-product/add-product.component';
import { MyListAndHistoryComponent } from './components/my-list-and-history/my-list-and-history.component';
import { ShowOneUserComponent } from './components/show-one-user/show-one-user.component';
import { ParticipateComponent } from './components/participate/participate.component';
import { NgxStripeModule } from 'ngx-stripe';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { ProductByCategoryComponent } from './components/product-by-category/product-by-category.component';

import { ShowDetailsProductForBuyerComponent } from './components/show-details-product-for-buyer/show-details-product-for-buyer.component';
import { LoginAdminComponent } from './components/login-admin/login-admin.component';
import { SideBarComponent } from './components/side-bar/side-bar.component';
import { PurchaseConfirmationComponent } from './components/purchase-confirmation/purchase-confirmation.component';
import { ShowOneProductComponent } from './components/show-one-product/show-one-product.component';
import { EditprofilebuyerComponent } from './components/editprofilebuyer/editprofilebuyer.component';
import { EditprofilesellerComponent } from './components/editprofileseller/editprofileseller.component';
import { ProductByCategoryAuctionComponent } from './components/product-by-category-auction/product-by-category-auction.component';





@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginRegisterComponent,
    DashboardUserComponent,
    SellerDashboardComponent,
    NavbarSellerComponent,
    EditProfileComponent,
    AddProductComponent,
    MyListAndHistoryComponent,
    ShowOneUserComponent,
    ParticipateComponent,
    LandingPageComponent,
    ProductByCategoryComponent,
    ShowDetailsProductForBuyerComponent,
    LoginAdminComponent,
    SideBarComponent,
    PurchaseConfirmationComponent,
    ShowOneProductComponent,
    EditprofilebuyerComponent,
    EditprofilesellerComponent,
    ProductByCategoryAuctionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    CommonModule,
    ReactiveFormsModule,

    NgxStripeModule.forRoot('sk_test_51OAa7NDYAUChbq42ItKiRoX53ZkDF1u6RFqREYaxszpJkLItO2iT8SDrpN1QtFlhRwA2Uzf41YHBgXir5OqAAr4o002LWEUHFc')
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
