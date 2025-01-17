export const ORDERS = [
  {
    id: 1,
    email: "customer1@example.com",
    address: "서울",
    zipCode: "111111",
    products: [
      { id: 0, name: "Columbia Nariñó", price: 5000, quantity: 1 },
      { id: 1, name: "Brazil Serra Do Caparaó", price: 6000, quantity: 2 },
    ],
    createdAt: "2025-01-16T13:30:00",
    price: 11000,
    status: true,
  },
  {
    id: 2,
    email: "customer2@example.com",
    address: "서울",
    zipCode: "111111",
    products: [{ id: 2, name: "Columbia Quindio", price: 7000, quantity: 1 }],
    createdAt: "2025-01-17T15:00:00",
    price: 7000,
    status: false,
  },
  {
    id: 3,
    email: "customer1@example.com",
    address: "서울",
    zipCode: "111111",
    products: [{ id: 3, name: "Ethiopia Sidamo", price: 8000, quantity: 3 }],
    createdAt: "2025-01-17T11:00:00",
    price: 24000,
    status: false,
  },
];
