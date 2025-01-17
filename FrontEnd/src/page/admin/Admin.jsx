import React, { useState, useEffect } from "react";
import "./Admin.css";
import { DeleteIcon, EditIcon } from "../../constant/Icon";

const AdminPage = () => {
  const [customerList, setCustomerList] = useState([]);
  const [filteredCustomerList, setFilteredCustomerList] = useState([]);
  const [editingCustomer, setEditingCustomer] = useState(null);
  const [editedData, setEditedData] = useState({
    name: "",
    email: "",
    purchaseTime: "",
  });

  useEffect(() => {
    const customers = [
      {
        id: 1,
        name: "고객1",
        email: "customer1@example.com",
        purchaseTime: "2025-01-17T14:30:00",
      },
      {
        id: 2,
        name: "고객2",
        email: "customer2@example.com",
        purchaseTime: "2025-01-17T15:00:00",
      },
      {
        id: 3,
        name: "고객3",
        email: "customer3@example.com",
        purchaseTime: "2025-01-17T13:00:00",
      },
    ];

    const afternoon2PM = new Date();
    afternoon2PM.setHours(14, 0, 0, 0);

    const filteredCustomers = customers.filter((customer) => {
      const purchaseTime = new Date(customer.purchaseTime);
      return purchaseTime >= afternoon2PM;
    });

    setCustomerList(customers);
    setFilteredCustomerList(filteredCustomers);
  }, []);

  const handleEditClick = (customer) => {
    setEditingCustomer(customer);
    setEditedData({
      name: customer.name,
      email: customer.email,
      purchaseTime: customer.purchaseTime,
    });
  };

  const handleEditChange = (e) => {
    const { name, value } = e.target;
    setEditedData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSaveEdit = (e) => {
    e.preventDefault();
    const updatedCustomerList = customerList.map((customer) =>
      customer.id === editingCustomer.id
        ? { ...customer, ...editedData }
        : customer
    );
    setCustomerList(updatedCustomerList);
    setEditingCustomer(null); // 수정 모드 종료
  };

  // 고객 삭제
  const handleDelete = (id) => {
    const updatedCustomerList = customerList.filter(
      (customer) => customer.id !== id
    );
    setCustomerList(updatedCustomerList);
    // 삭제된 고객은 필터링된 리스트에서도 제외
    const updatedFilteredList = filteredCustomerList.filter(
      (customer) => customer.id !== id
    );
    setFilteredCustomerList(updatedFilteredList);
  };

  return (
    <main className='admin-wrap'>
      <h2>Admin</h2>
      <h3>오후 2시 이후 구매한 고객 리스트</h3>

      <div className='table-container'>
        <table>
          <thead>
            <tr>
              <th>고객 ID</th>
              <th>이메일</th>
              <th>구매 시간</th>
              <th>수정</th>
              <th>삭제</th>
            </tr>
          </thead>
          <tbody>
            {filteredCustomerList.map((customer) => (
              <tr key={customer.id}>
                <td>{customer.id}</td>
                <td>{customer.email}</td>
                <td>{new Date(customer.purchaseTime).toLocaleString()}</td>
                <td>
                  <button
                    className='edit-btn'
                    onClick={() => handleEditClick(customer)}>
                    <EditIcon />
                  </button>
                </td>
                <td>
                  <button
                    className='delete-btn'
                    onClick={() => handleDelete(customer.id)}>
                    <DeleteIcon />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {editingCustomer && (
        <div className='edit-form-container'>
          <h3>고객 수정</h3>
          <form className='edit-form' onSubmit={handleSaveEdit}>
            <div className='edit-form-group'>
              <label>이름</label>
              <input
                type='text'
                name='name'
                value={editedData.name}
                onChange={handleEditChange}
              />
            </div>
            <div className='edit-form-group'>
              <label>이메일</label>
              <input
                type='email'
                name='email'
                value={editedData.email}
                onChange={handleEditChange}
              />
            </div>
            <div className='edit-form-group'>
              <label>구매 시간</label>
              <input
                type='datetime-local'
                name='purchaseTime'
                value={editedData.purchaseTime}
                onChange={handleEditChange}
              />
            </div>
            <button type='submit' className='save-btn'>
              저장
            </button>
            <button
              type='button'
              className='cancel-btn'
              onClick={() => setEditingCustomer(null)}>
              취소
            </button>
          </form>
        </div>
      )}
    </main>
  );
};

export default AdminPage;
