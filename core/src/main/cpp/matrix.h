#pragma once
#ifndef MATRIX_H
#define MATRIX_H

#include <vector>
#include <iostream> 
#include <string>

class Matrix {
private:

public:
    int rows;
    int cols;
    std::vector<std::vector<double>> data;

    Matrix(int rows, int cols);
    Matrix(const std::vector<std::vector<double>>& initData);
    Matrix add(const Matrix& other) const;
    Matrix subtract(const Matrix& other) const;
    Matrix multiply(const Matrix& other) const;
    Matrix transpose() const;
    int rank() const;
    double determinant() const;
    friend std::ostream& operator<<(std::ostream& os, const Matrix& matrix);
};

std::string matrix_library_name();

#endif // MATRIX_H
