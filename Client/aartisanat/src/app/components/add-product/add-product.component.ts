import { User } from 'src/app/models/user.model';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Product } from 'src/app/models/product.model';
import { ProductsService } from 'src/app/services/product.service';
import { UsersService } from 'src/app/services/user.service';
import { WalletsService } from 'src/app/services/wallet.service';
import { loadStripe } from '@stripe/stripe-js';

@Component({
    selector: 'app-add-product',
    templateUrl: './add-product.component.html',
    styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent {

    showAdditional: boolean = false; // Used to toggle additional fields
    product: any = {};
    selectedFiles: File[] = [];
    dashboardData: any;
    amount: any;
    duration: any;
    isChecked: boolean = false;
    withdrow: any;
    errorMessage?: String;
    fieldErrors: { [key: string]: string } = {};


    constructor(
        private fb: FormBuilder,
        private productService: ProductsService,
        private userService: UsersService,
        private walletService: WalletsService,
        private http: HttpClient,
        private router: Router
    ) {
    }

    checkStatus(event: Event) {
        const isChecked = (event.target as HTMLInputElement).checked;
        console.log('Checkbox is checked:', isChecked);
    }
    toggleAdditionalFields() {
        this.showAdditional = !this.showAdditional;
    }
    ngOnInit() {
        this.userService.getDashboard().subscribe({
            next: (data) => {
                this.dashboardData = data;
                console.log(JSON.stringify(data));


                console.log(sessionStorage.getItem('user_id'));
            },
            error: (error) => {
                console.error('There was an error!', error);
            }
        });
    }
    onFilesSelected(event: any): void {
        const files: FileList = event.target.files;
        if (files) {
            this.selectedFiles = Array.from(files); // Convert FileList to an array
        }
    }

    saveProduct() {
        const formData = new FormData();

        if (this.isChecked == true) {
            this.product.typeProduct = true;
        }
        else {
            this.product.typeProduct = false;
        }
        if (this.product.typeProduct == true) {
            this.product.price = 1;
        }

        formData.append('product', new Blob([JSON.stringify(this.product)], { type: 'application/json' }))
        this.selectedFiles.forEach((file) => {
            formData.append('files', file, file.name);
        });

        this.productService.registerProduct(formData).subscribe({
            next: (response) => {
                console.log('Product registered successfully', response);
                this.router.navigateByUrl("/sellerDashborad");
            },
            error: (error) => {
                console.error('Product registration failed:', error);
                if (error.status === 400 && error.error && error.error.message === "Required part 'files' is not present.") {
                    this.errorMessage = "Files are required.";
                }

                if (error.status === 400 && error.error && Array.isArray(error.error)) {
                    // Clear existing errors
                    this.fieldErrors = {};
    
                    // Process each field error
                    error.error.forEach((fieldError: { field: any; defaultMessage: any; }) => {
                        const field = fieldError.field;
                        const message = fieldError.defaultMessage;
    
                        // Store the error message for the corresponding field
                        this.fieldErrors[field] = message;
                    });
                }
            }
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
        this.router.navigateByUrl('/login');
    }
    AddBalance() {
        console.log(this.amount);
        this.walletService.configWallet(this.amount).subscribe({
            next: async (response) => {
                // Load the Stripe.js library
                const stripe = await loadStripe('pk_test_51OAa7NDYAUChbq42qUbpfV0sCVxEL7NCccBnfuZRUyK5qG8jupanv6IHxa3erOvExCXALbddf2zDn4C10QZTEXh300mWuvmhUK');
                const sessionId = response.sessionId;
                if (stripe) {
                    // Redirect to Stripe Checkout
                    stripe.redirectToCheckout({ sessionId });
                } else {
                    console.error('Stripe.js failed to load');
                }
            },
            error: (error) => {
                console.error('There was an error!', error);
            }

        });
    }
    withdrawBalance() {
        console.log(this.withdrow);
        this.walletService.withdraw(this.withdrow).subscribe({
            next: async (response) => {
                // Load the Stripe.js library
                const stripe = await loadStripe('pk_test_51OAa7NDYAUChbq42qUbpfV0sCVxEL7NCccBnfuZRUyK5qG8jupanv6IHxa3erOvExCXALbddf2zDn4C10QZTEXh300mWuvmhUK');
                const sessionId = response.sessionId;
                if (stripe) {
                    // Redirect to Stripe Checkout
                    stripe.redirectToCheckout({ sessionId });
                } else {
                    console.error('Stripe.js failed to load');
                }
            },
            error: (error) => {
                console.error('There was an error!', error);
            }

        });
    }
}
